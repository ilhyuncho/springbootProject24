package com.example.cih.domain.car;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "car")
@Table(name="carConsumables")
public class CarConsumable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="CONSUMABLE_ID")
    private Long consumableId;

    private Long refConsumableId;
    private LocalDate replaceDate;      // 점검 날짜
    private int replacePrice;
    private int accumKm;                // 누적 거리
    private String replaceShop;         // 점검 장소
    private int gasLitter;

    @Enumerated(EnumType.STRING)
    private RepairType repairType;          // 정비 종류

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "carId")
    private Car car;
}
