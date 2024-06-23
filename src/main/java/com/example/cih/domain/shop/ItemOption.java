package com.example.cih.domain.shop;


import com.example.cih.common.exception.NotEnoughStockCountException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="itemOptions")
public class ItemOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="itemOptionId")
    private Long itemOptionId;

    private ItemOptionType type;
    private String option1;
    private String option2;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SHOP_ITEM_ID")
    private ShopItem shopItem;

    public void changeItem(ShopItem shopItem){
        this.shopItem = shopItem;
    }
}
