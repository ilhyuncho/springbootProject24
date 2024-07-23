package com.example.cih.domain.shop;


import com.example.cih.domain.common.BaseEntity;
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
@Table(name="itemImages")
@ToString
public class ItemImage extends BaseEntity {

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

    private Boolean isMainImage;

    public void changeItem(ShopItem shopItem){
        this.shopItem = shopItem;
    }
    public void changeImageOrder(int imageOrder){
        this.imageOrder = imageOrder;
    }

}
