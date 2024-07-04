package com.example.cih.dto.car;

import com.example.cih.domain.carConsumable.ReplaceAlarm;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CarConsumableResDTO {
    private Long consumableId;
    private String name;
    private String repairType;
    private int replaceCycleKm;
    private int replaceCycleMonth;
    private int replacePrice;
    private int accumKm;
    private String replaceShop;
    private LocalDate replaceDate;
    private int viewOrder;

    @Enumerated(EnumType.STRING)
    private ReplaceAlarm replaceAlarm;

    public void setReplaceInfo(int replacePrice, int accumKm, String replaceShop, LocalDate replaceDate){
        this.replacePrice = replacePrice;
        this.replaceShop = replaceShop;
        this.accumKm = accumKm;
        this.replaceDate = replaceDate;
    }

    public void setReplaceAlarm(ReplaceAlarm replaceAlarm){
        this.replaceAlarm = replaceAlarm;
    }

}