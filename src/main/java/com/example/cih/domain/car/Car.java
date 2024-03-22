package com.example.cih.domain.car;


import com.example.cih.common.CarSizeConverter;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name="Cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="carId", length = 50, nullable = false)
    private Long carId;

    @Column(name="carNumber", length = 50, nullable = false)
    private String carNumber;

    @Column(name="carGrade", length = 4, nullable = false)
    @Convert(converter= CarSizeConverter.class)
    private CarSize carGrade;

    @Column(name="carModel", length = 20, nullable = false)
    private String carModel;

    @Column(name="carYears", nullable = false)
    private int carYears;

    @Column(name="carColors", length = 10, nullable = false)
    private String carColors;

    @Column(name="carKm", nullable = false)
    private Long carKm;

    @Column(name="userId", nullable = false)
    private Long userId;
}
