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
    private String replaceShop;         // 점검 장소
    private int accumKm;                // 누적 거리
    private int replacePrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "carId")
    private Car car;

//    public void changeReplaceInfo(int replacePrice, int accumKm, String replaceShop, LocalDate replaceDate){
//        this.replacePrice = replacePrice;
//        this.replaceShop = replaceShop;
//        this.accumKm = accumKm;
//        this.replaceDate = replaceDate;
//    }
}
