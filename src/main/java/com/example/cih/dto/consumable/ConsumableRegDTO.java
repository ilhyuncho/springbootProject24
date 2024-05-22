package com.example.cih.dto.consumable;

import com.example.cih.domain.car.RepairType;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ConsumableRegDTO {

    private Long consumableId;
    private Long carId;
    private int replacePrice;
    private int accumKm;
    private int gasLitter;
    private String replaceShop;

    @Enumerated(EnumType.STRING)
    private RepairType repairType;

    @NotNull
    private LocalDate replaceDate;
}