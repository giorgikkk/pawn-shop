package com.example.domain;

import com.example.domain.enums.OdometerUnitType;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "car")
public class Car extends Item {

    @Column(name = "car_model", nullable = false, length = 128)
    @NotBlank
    @Length(max = 128)
    private String model;

    @Column(name = "car_release_date", nullable = false)
    @NotNull
    @Past
    private LocalDate releaseDate;

    @Column(name = "car_odometer_unit_type", nullable = false)
    @NotNull
    @Enumerated(EnumType.STRING)
    private OdometerUnitType type = OdometerUnitType.KILOMETERS;

    @Column(name = "car_odometer_display", nullable = false)
    @Min(0)
    private long odometerDisplay;
}
