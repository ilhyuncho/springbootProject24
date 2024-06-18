package com.example.cih.domain.shop;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="itemPrices")
public class ItemPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ITEM_PRICE_ID")
    private Long itemPriceId;

    private Integer originalPrice;
    private Integer membershipPercent;

    private Integer salePercent;
    private LocalDateTime saleStartDate;
    private LocalDateTime saleEndDate;

//    @Column(columnDefinition = "boolean default 0")
//    @Builder.Default
//    private Boolean isOnSale = false;

}


