package com.example.cih.dto.car;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CarConsumableInfoDTO {
    private Long consumableId;
    private String repairType;
    private int replacePrice;
    private int accumKm;
    private String replaceShop;
    private LocalDateTime replaceDate;
}