package com.example.cih.service.auction;

import com.example.cih.dto.auction.AuctionRegDTO;
import com.example.cih.dto.auction.AuctionViewDTO;

public interface AuctionService {
    void registerAuction(String userName, AuctionRegDTO auctionRegDTO);
    AuctionViewDTO getAuction(Long auctionId);
}
