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

    private Long refConsumableId;

    private LocalDateTime replaceDate;  // 마지막 점검 날짜

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "carId")
    private Car car;

    public void changeReplaceDate(LocalDateTime replaceDate){
        this.replaceDate = replaceDate;
    }
}
