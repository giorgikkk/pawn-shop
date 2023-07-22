package com.example.domain;

import com.example.domain.enums.TechniqueType;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "domestic_appliance")
public class DomesticAppliance extends Item {

    @Column(name = "domestic_appliances_manufacturing_firm", nullable = false, length = 64)
    @NotBlank
    @Length(max = 64)
    private String manufacturingFirm;

    @Column(name = "domestic_appliances_type", nullable = false)
    @NotNull
    @Enumerated(EnumType.STRING)
    private TechniqueType type;

    @Column(name = "domestic_appliances_damage", nullable = false)
    @NotNull
    private boolean isDamaged;

    @Column(name = "domestic_appliances_damage_description")
    private String damageDescription;
}
