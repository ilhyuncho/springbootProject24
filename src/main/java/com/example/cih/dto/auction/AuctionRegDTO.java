package com.example.cih.dto.auction;

import lombok.*;

import javax.validation.constraints.Min;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AuctionRegDTO {

    private Long carId;
    @Min(value=100_000, message = "최소 100000원 이상")
    private int requiredPrice;

}
