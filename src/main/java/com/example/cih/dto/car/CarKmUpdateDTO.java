package com.example.cih.dto.car;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CarKmUpdateDTO {
    private Long carId;
    private Long updateKmValue;
}