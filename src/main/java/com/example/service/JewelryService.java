package com.example.service;

import com.example.exceptions.ItemNotFoundException;
import com.example.repository.JewelryRepository;
import com.example.domain.Jewelry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class JewelryService {

    private final JewelryRepository repo;

    @Autowired
    public JewelryService(JewelryRepository repo) {
        this.repo = repo;
    }

    @Cacheable("jewelries")
    public List<Jewelry> getAll(final int page, final int pageSize) {
        log.info("getting jewelries on relevant page");

        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        Pageable pageable = PageRequest.of(page, pageSize, sort);

        return repo.findAll(pageable)
                .stream()
                .collect(Collectors.toList());
    }

    public Jewelry getById(final long id) {
        log.info("getting jewelry by id");

        return repo.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(String.format("jewelry with id:%d not found", id)));
    }

    @CacheEvict(value = "jewelries", allEntries = true)
    public Jewelry create(final Jewelry jewelry) {
        log.info("creating new jewelry");

        return repo.save(jewelry);
    }

    @CacheEvict(value = "jewelries", allEntries = true)
    public Jewelry update(final long id, final Jewelry jewelry) {
        log.info("updating jewelry");

        Optional<Jewelry> jewelryOptional = repo.findById(id);
        jewelryOptional.ifPresent(j -> {
            jewelry.setId(id);
            repo.save(jewelry);
        });

        return jewelryOptional
                .orElseThrow(() -> new ItemNotFoundException(String.format("jewelry with id:%d not found", id)));
    }

    @CacheEvict(value = "jewelries", allEntries = true)
    public Jewelry delete(final long id) {
        log.info("removing jewelry");

        Optional<Jewelry> jewelry = repo.findById(id);
        jewelry.ifPresent(j -> repo.deleteById(j.getId()));

        return jewelry
                .orElseThrow(() -> new ItemNotFoundException(String.format("jewelry with id:%d not found", id)));
    }
}
