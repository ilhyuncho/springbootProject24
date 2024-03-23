package com.example.cih.domain.sellingCar;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name="SellingCars")
public class SellingCar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="sellingId", length = 50, nullable = false)
    private Long sellingId;

    private SellingType sellingType;

    private LocalDateTime expiredDate;

    @Column(name="userId", nullable = false)
    private Long userId;
}
