package com.example.cih.dto.buyingCar;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BuyingCarRegDTO {
    private Long carId;
    private int requestPrice;
}
