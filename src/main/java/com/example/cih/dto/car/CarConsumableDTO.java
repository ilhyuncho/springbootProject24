package com.example.cih.dto.car;

import lombok.*;

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
    private LocalDateTime replaceDate;
    private int viewOrder;
}