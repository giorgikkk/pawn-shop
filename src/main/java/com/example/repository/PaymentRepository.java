package com.example.repository;

import com.example.domain.Payment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;

@Repository
public interface PaymentRepository extends PagingAndSortingRepository<Payment, Long> {
    @Query("SELECT SUM(p.amountOfMoney) " +
            "FROM Payment p " +
            "WHERE p.item.id = :itemId " +
            "AND p.paymentDate BETWEEN :startOfTheMonth AND :endOfTheMonth")
    BigDecimal getPaymentAmountInMonthForItem(long itemId,
                                              LocalDate startOfTheMonth,
                                              LocalDate endOfTheMonth);
}
