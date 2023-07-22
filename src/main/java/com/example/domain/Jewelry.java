package com.example.domain;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "jewelry")
public class Jewelry extends Item {

    @Column(name = "jewelry_description", nullable = false, length = 255)
    @NotBlank
    @Length(max = 255)
    private String description;

    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinTable(
            name = "jewelry_material_map",
            joinColumns = @JoinColumn(name = "jewelry_id", referencedColumnName = "item_id"),
            inverseJoinColumns = @JoinColumn(name = "material_id", referencedColumnName = "material_id")
    )
    private Set<Material> materials;
}
