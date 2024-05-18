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
    private String type;
    private String name;
    private int replaceCycle;
    private LocalDateTime replaceDate;
}