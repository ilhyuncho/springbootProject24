package com.example.cih.dto.car;

import com.example.cih.domain.car.ReplaceAlarm;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CarConsumableDTO {
    private Long consumableId;
    private String name;
    private String repairType;
    private int replaceCycleKm;
    private int replaceCycleMonth;
    private int replacePrice;
    private int accumKm;
    private String replaceShop;
    private LocalDateTime replaceDate;
    private int viewOrder;

    @Enumerated(EnumType.STRING)
    private ReplaceAlarm replaceAlarm;

    public void changeReplaceInfo(int replacePrice, int accumKm, String replaceShop, LocalDateTime replaceDate){
        this.replacePrice = replacePrice;
        this.replaceShop = replaceShop;
        this.accumKm = accumKm;
        this.replaceDate = replaceDate;
    }

    public void setReplaceAlarm(ReplaceAlarm replaceAlarm){
        this.replaceAlarm = replaceAlarm;
    }

}