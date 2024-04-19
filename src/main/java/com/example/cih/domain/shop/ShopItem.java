package com.example.cih.domain.shop;


import com.example.cih.common.exception.NotEnoughStockCountException;
import com.example.cih.domain.user.User;
import lombok.*;
import lombok.experimental.SuperBuilder;
import net.bytebuddy.matcher.FilterableList;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="shopItems")
public class ShopItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="SHOP_ITEM_ID")
    private Long shopItemId;

    private String itemName;
    private int price;
    private int stockCount;     // 재고수량

//    @ManyToMany(mappedBy = "")
//    @JoinColumn(name = "uId")
//    private List<Category> categoryList = new ArrayList<>();

    public int removeStock(int count) {
        if( stockCount < count){
            throw new NotEnoughStockCountException("need more Stock");
        }
        stockCount -= count;
        return stockCount;
    }
}
