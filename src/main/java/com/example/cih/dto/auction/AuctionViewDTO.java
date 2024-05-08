package com.example.cih.dto.auction;

import com.example.cih.domain.auction.AuctionStatus;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AuctionViewDTO {
    private Long carId;
    private AuctionStatus auctionStatus;
    private int requiredPrice;
    private LocalDateTime expiredDate;
}
