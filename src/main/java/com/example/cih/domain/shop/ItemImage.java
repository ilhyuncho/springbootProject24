package com.example.cih.domain.shop;


import com.example.cih.domain.car.Car;
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
@Table(name="itemImages")
public class ItemImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="itemImageId")
    private Long itemImageId;

    private String uuid;

    private String fileName;

    private int imageOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SHOP_ITEM_ID")
    ShopItem shopItem;

    public void changeItem(ShopItem shopItem){
        this.shopItem = shopItem;
    }




}
