package com.example.cih.dto.car;

import com.example.cih.domain.carConsumable.CarConsumable;
import com.example.cih.domain.carConsumable.ReplaceAlarm;
import com.example.cih.domain.reference.RefCarConsumable;
import com.example.cih.service.common.CommonUtils;
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

    private Long refConsumableId;
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

    // 최근 정비 내역 set
    public void setRecentReplaceInfo(RefCarConsumable refCarConsumable, CarConsumable carConsumable){

        this.setReplaceAlarm(CommonUtils.checkNextReplaceDay(refCarConsumable, carConsumable));

        this.setReplaceInfo(carConsumable.getReplacePrice(), carConsumable.getAccumKm(),
                carConsumable.getReplaceShop(), carConsumable.getReplaceDate() );
    }

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