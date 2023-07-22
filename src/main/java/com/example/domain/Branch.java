package com.example.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "branch")
public class Branch {

    @Id
    @Column(name = "branch_id")
    @EqualsAndHashCode.Include
    @Min(1)
    private long id;

    @Column(name = "branch_address", nullable = false, length = 255)
    @NotBlank
    @Length(max = 255)
    private String address;

    @JsonIgnore
    @OneToMany(mappedBy = "branch", cascade = CascadeType.REMOVE)
    private Set<Item> items;
}
