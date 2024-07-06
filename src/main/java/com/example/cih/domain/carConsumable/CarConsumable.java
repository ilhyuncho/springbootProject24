package com.example.cih.domain.carConsumable;


import com.example.cih.domain.car.Car;
import com.example.cih.domain.reference.RefCarConsumable;
import com.example.cih.dto.car.CarConsumableRegDTO;
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

    @Enumerated(EnumType.STRING)
    private ConsumableType consumableType;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="REF_CONSUMABLE_ID")   // pk(외래키)가 RefCarConsumable 테이블(주테이블)에 생성
    private RefCarConsumable refCarConsumable;

    private LocalDate replaceDate;      // 점검 날짜
    private int replacePrice;
    private int accumKm;                // 누적 거리
    private String replaceShop;         // 점검 장소
    private int gasLitter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "carId")
    private Car car;

    // 소모품 내역 수정
    public void setConsumableInfo(CarConsumableRegDTO carConsumableRegDTO){

        this.replaceDate = carConsumableRegDTO.getReplaceDate();
        this.replacePrice = carConsumableRegDTO.getReplacePrice();
        this.replaceShop = carConsumableRegDTO.getReplaceShop();
        this.accumKm = carConsumableRegDTO.getAccumKm();
    }
}
