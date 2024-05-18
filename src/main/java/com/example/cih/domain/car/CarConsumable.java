package com.example.cih.domain.car;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    private String name;
    private String repairType;      // 점검 or 교체
    private int replaceCycleKm;
    private int replaceCycleMonth;
    private LocalDateTime replaceDate;  // 마지막 점검 날짜

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "carId")
    private Car car;
}
