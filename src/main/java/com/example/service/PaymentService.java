package com.example.service;

import com.example.exceptions.ItemNotFoundException;
import com.example.exceptions.PaymentNotFoundException;
import com.example.repository.ItemRepository;
import com.example.repository.PaymentRepository;
import com.example.domain.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PaymentService {
    private final PaymentRepository repo;
    private final ItemRepository itemRepository;

    @Autowired
    public PaymentService(PaymentRepository repo, ItemRepository itemRepository) {
        this.repo = repo;
        this.itemRepository = itemRepository;
    }

    @Cacheable("payments")
    public List<Payment> getAll(final int page, final int pageSize) {
        log.info("getting payments on relevant page");

        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        Pageable pageable = PageRequest.of(page, pageSize, sort);

        return repo.findAll(pageable)
                .stream()
                .collect(Collectors.toList());
    }

    public Payment getById(final long id) {
        log.info("getting payment by id");

        return repo.findById(id)
                .orElseThrow(() -> new PaymentNotFoundException(String.format("payment with id:%d not found", id)));
    }

    @CacheEvict(value = "payments", allEntries = true)
    public Payment create(final Payment payment) {
        log.info("creating new payment");

        return repo.save(payment);
    }

    @CacheEvict(value = "payments", allEntries = true)
    public Payment update(final long id, final Payment payment) {
        log.info("updating payment");

        Optional<Payment> paymentOptional = repo.findById(id);
        paymentOptional.ifPresent(j -> {
            payment.setId(id);
            repo.save(payment);
        });

        return paymentOptional
                .orElseThrow(() -> new PaymentNotFoundException(String.format("payment with id:%d not found", id)));
    }

    @CacheEvict(value = "payments", allEntries = true)
    public Payment delete(final long id) {
        log.info("removing payment");

        Optional<Payment> payment = repo.findById(id);
        payment.ifPresent(p -> repo.deleteById(p.getId()));

        return payment
                .orElseThrow(() -> new PaymentNotFoundException(String.format("payment with id:%d not found", id)));
    }

    public BigDecimal getPaymentAmountInMonthForItem(long itemId,
                                                     LocalDate startOfTheMonth,
                                                     LocalDate endOfTheMonth) {
        if (!itemRepository.existsById(itemId)) {
            throw new ItemNotFoundException(String.format("item with id:%d does not exist", itemId));
        }

        return repo.getPaymentAmountInMonthForItem(itemId, startOfTheMonth, endOfTheMonth);
    }

    @Scheduled(cron = "${cron.expression}")
    public void checkForPayment() {
        itemRepository.getAllItem()
                .stream()
                .filter(item -> item.getPawnDate().getDayOfMonth() == LocalDate.now().getDayOfMonth())
                .filter(item -> {
                    BigDecimal currMonthPayment = getPaymentAmountInMonthForItem(item.getId(), LocalDate.now().minusMonths(1), LocalDate.now());
                    return currMonthPayment.compareTo(item.getMonthFee()) < 0;
                })
                .forEach(item -> {
                    item.setConfiscated(true);
                    itemRepository.save(item);
                });
    }
}
