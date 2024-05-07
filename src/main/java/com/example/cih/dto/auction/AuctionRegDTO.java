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
    private int requiredPrice;
}
