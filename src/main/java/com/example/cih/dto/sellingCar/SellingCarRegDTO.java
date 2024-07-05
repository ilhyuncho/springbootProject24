package com.example.cih.dto.sellingCar;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SellingCarRegDTO {

    private Long carId;
    private int requiredPrice;
    private Boolean isLike;
}
