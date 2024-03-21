package com.example.cih.domain.car;


import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long carId;

    @Column(name="carNumber", length = 50, nullable = false)
    private String carNumber;

    @Column(name="carGrade", length = 20, nullable = false)
    private String carGrade;

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
