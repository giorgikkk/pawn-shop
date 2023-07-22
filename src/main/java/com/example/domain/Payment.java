package com.example.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Table(name = "payment")
public class Payment {

    @Id
    @Column(name = "payment_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payment_id_gen")
    @SequenceGenerator(name = "payment_id_gen", sequenceName = "payment_id_seq", allocationSize = 1)
    @EqualsAndHashCode.Include
    private long id;

    @Column(name = "payment_date", nullable = false)
    @NotNull
    @Past
    private LocalDate paymentDate;

    @Column(name = "payment_amount", nullable = false)
    @NotNull
    private BigDecimal amountOfMoney;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "item_id", nullable = false)
    @NotNull
    private Item item;
}
