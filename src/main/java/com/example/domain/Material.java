package com.example.domain;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "material")
public class Material {

    @Id
    @Column(name = "material_id")
    @EqualsAndHashCode.Include
    @Min(1)
    private long id;

    @Column(name = "material_name", nullable = false, length = 64)
    @NotBlank
    @Length(max = 64)
    private String name;
}
