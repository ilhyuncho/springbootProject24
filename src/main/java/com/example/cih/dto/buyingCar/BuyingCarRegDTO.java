package com.example.cih.dto.buyingCar;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BuyingCarRegDTO {
    private Long carId;
    private Long sellingCarId;
    private int requestPrice;
    private String phoneNumber;
    private String offerType;
    private String consultText;

}
