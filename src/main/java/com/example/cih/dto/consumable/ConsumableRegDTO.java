package com.example.cih.dto.consumable;

import com.example.cih.domain.carConsumable.ConsumableType;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ConsumableRegDTO {

    private Long consumableId;
    private ConsumableType consumableType;
    private Long carId;
    private int replacePrice;
    private int accumKm;
    private int gasLitter;
    private String replaceShop;

    @NotNull
    private LocalDate replaceDate;
}