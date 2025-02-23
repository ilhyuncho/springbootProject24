package com.example.cih.domain.shop;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="itemOptions")
@ToString
public class ItemOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="itemOptionId")
    private Long itemOptionId;

    private ItemOptionType itemOptionType;
    private String optionName;
    private Integer typePriority;
    private Integer optionOrder;

   // private String option2;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SHOP_ITEM_ID")
    private ShopItem shopItem;

    public void changeItem(ShopItem shopItem){
        this.shopItem = shopItem;
    }
}
