package com.example.cih.domain.auction;


import com.example.cih.common.exception.NotEnoughStockCountException;
import com.example.cih.domain.car.Car;
import com.example.cih.domain.shop.ItemImage;
import com.example.cih.domain.shop.ItemOption;
import com.example.cih.domain.shop.Order;
import com.example.cih.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="auctions")
public class Auction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="AUCTION_ID")
    private Long auctionid;

    private AuctionStatus auctionStatus;

    private int RequiredPrice;

    @OneToOne(mappedBy = "auction")
    private Car car;

    @ManyToOne(fetch = FetchType.LAZY)   // 일단 @ManyToOne 단방향
    @JoinColumn(name="uId")
    private User user;

}
