package com.example.cih.service.auction;

import com.example.cih.dto.auction.AuctionRegDTO;

public interface AuctionService {
    Long registerAuction(String userName, AuctionRegDTO auctionRegDTO);
}
