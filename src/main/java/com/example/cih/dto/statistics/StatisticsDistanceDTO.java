package com.example.cih.dto.statistics;

import com.example.cih.domain.carConsumable.ConsumableType;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StatisticsDistanceDTO {
    private ConsumableType consumableType;
    private LocalDate eventDate;
    private int eventValue;
}
