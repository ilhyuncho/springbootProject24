package com.example.cih.dto.sellingCar;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BuyRequestRegDTO {
    private Long carId;
    private int requestPrice;
}
