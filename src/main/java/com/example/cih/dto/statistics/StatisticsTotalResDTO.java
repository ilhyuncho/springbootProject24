package com.example.cih.dto.statistics;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StatisticsTotalResDTO {
    private int gasCost;        // 총 주유비
    private int gasAmount;      // 총 주유량
    private int repairCost;     // 총 정비 비용
    private int accKm;          // 총 운행거리
}
