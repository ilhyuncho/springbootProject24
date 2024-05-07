package com.example.cih.dto.auction;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AuctionViewDTO {
    private Long carId;
    private int requiredPrice;
}
