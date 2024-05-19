package com.example.cih.dto.consumable;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ConsumableRegDTO {

    private Long consumableId;
    private Long carId;
    private LocalDateTime replaceDate;
}