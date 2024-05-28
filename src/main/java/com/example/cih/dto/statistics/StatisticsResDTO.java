package com.example.cih.dto.statistics;

import com.example.cih.domain.carConsumable.ConsumableType;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StatisticsResDTO {

    private String eventDate;
    private int eventValue;
    private ConsumableType consumableType;

}
