package com.example.cih.dto.statistics;

import com.example.cih.domain.carConsumable.ConsumableType;
import lombok.*;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StatisticsTotalDTO {

    private ConsumableType consumableType;
    private int gasAmount;          // 주유 량
    private int cost;               // 결제 금액
    private int accKm;              // 누적 주행 거리
}
