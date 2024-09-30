package com.example.cih.dto.car;

import lombok.*;

//@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class CarKmUpdateReqDTO {
    private Long carId;
    private Long updateKmValue;
}