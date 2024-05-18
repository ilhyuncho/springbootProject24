package com.example.cih.dto.car;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CarConsumableDTO {
    private Long consumableId;
    private String name;
    private String repairType;
    private int replaceCycleKm;
    private int replaceCycleMonth;
    private LocalDateTime replaceDate;
}