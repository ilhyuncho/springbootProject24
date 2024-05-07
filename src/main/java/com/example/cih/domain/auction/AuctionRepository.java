package com.example.cih.domain.auction;

import com.example.cih.domain.shop.ShopItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuctionRepository extends JpaRepository<Auction, Long> {

}

