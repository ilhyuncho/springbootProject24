package com.example.cih.dto.statistics;

import com.example.cih.domain.carConsumable.ConsumableType;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StatisticsResDTO {
    private ConsumableType consumableType;
    private String eventDate;
    private int eventValue;
    private int chartBarOrder;
}
