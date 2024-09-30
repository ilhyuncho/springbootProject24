package com.example.cih.dto.car;

import lombok.*;

import java.time.LocalDate;

//@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class CarConsumableDetailResDTO {
    private Long carId;
    private Long refConsumableId;
    private Long consumableId;
    private int replacePrice;
    private int accumKm;
    private String replaceShop;
    private LocalDate replaceDate;
    private String repairName;
}