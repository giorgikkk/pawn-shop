package com.example.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "item")
@Inheritance(strategy = InheritanceType.JOINED)
public class Item {

    @Id
    @Column(name = "item_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "item_id_gen")
    @SequenceGenerator(name = "item_id_gen", sequenceName = "item_id_seq", allocationSize = 1)
    @EqualsAndHashCode.Include
    private long id;

    @Column(name = "item_price", nullable = false)
    @NotNull
    private BigDecimal price;

    @Column(name = "item_month_fee", nullable = false)
    @NotNull
    private BigDecimal monthFee;

    @Column(name = "item_pawn_date", nullable = false)
    @NotNull
    @Past
    private LocalDate pawnDate;

    @Column(name = "item_is_taken_out", nullable = false)
    @NotNull
    private boolean isTakeOut;

    @Column(name = "item_take_out_date")
    private LocalDate takeOutDate;

    @Column(name = "item_is_confiscated", nullable = false)
    @NotNull
    private boolean isConfiscated;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "owner_id", nullable = false)
    @NotNull
    private Owner owner;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "branch_id", nullable = false)
    @NotNull
    private Branch branch;

    @JsonIgnore
    @OneToMany(mappedBy = "item", cascade = CascadeType.REMOVE)
    private Set<Payment> payments;
}
