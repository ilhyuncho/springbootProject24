package com.example.cih.domain.shop;


import com.example.cih.common.exception.NotEnoughStockCountException;
import com.example.cih.domain.car.CarImage;
import com.example.cih.domain.car.CarSize;
import com.example.cih.domain.user.User;
import lombok.*;
import lombok.experimental.SuperBuilder;
import net.bytebuddy.matcher.FilterableList;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

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
    private int stockCount;     // 재고수량

    @OneToMany(mappedBy = "shopItem", //
            cascade = {CascadeType.ALL}, // ShopItem 엔티티에서 하위 엔티티 객체들을 관리 하는 기능을 추가 해서 사용
            fetch = FetchType.LAZY,
            orphanRemoval = true        // 하위 엔티티가 참조가 더 이상 없는 상태면 삭제 처리 해준다
    )
    @Builder.Default
    @BatchSize(size=20) // N번에 해당하는 쿼리를 모아서 한번에 실행, (N+1문제 해결)
    private Set<ItemImage> itemImageSet = new HashSet<>();

    @OneToMany(mappedBy = "shopItem", //
            cascade = {CascadeType.ALL}, // ShopItem 엔티티에서 하위 엔티티 객체들을 관리 하는 기능을 추가 해서 사용
            fetch = FetchType.LAZY,
            orphanRemoval = true        // 하위 엔티티가 참조가 더 이상 없는 상태면 삭제 처리 해준다
    )
    @Builder.Default
    @BatchSize(size=20) // N번에 해당하는 쿼리를 모아서 한번에 실행, (N+1문제 해결)
    private Set<ItemOption> itemOptionSet = new HashSet<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ITEM_PRICE_ID")
    private ItemPrice itemPrice;

//    @ManyToMany(mappedBy = "")
//    @JoinColumn(name = "uId")
//    private List<Category> categoryList = new ArrayList<>();

    public void change(String itemName, int price, int stockCount){
        this.itemName = itemName;
        //this.price = price;   // 임시로
        this.stockCount = stockCount;
    }

    //ShopItem 엔티티 에서 ItemImage 엔티티 객체들을 모두 관리  begin---------------
    public void addImage(String uuid, String fileName){
        ItemImage carImage = ItemImage.builder()
                .uuid(uuid)
                .fileName(fileName)
                .shopItem(this)
                .imageOrder(itemImageSet.size())
                .build();
        itemImageSet.add(carImage);
    }

    public void clearImages(){
        itemImageSet.forEach(image -> image.changeItem(null));
        this.itemImageSet.clear();
    }

    public void addItemOption(String option1, String option2){
        ItemOption itemOption = ItemOption.builder()
                .option1(option1)
                .option2(option2)
                .shopItem(this)
                .build();
        itemOptionSet.add(itemOption);
    }

    public void clearItemOption(){
        itemOptionSet.forEach(option -> option.changeItem(null));
        this.itemOptionSet.clear();
    }
    //ShopItem 엔티티 에서 ItemImage 엔티티 객체들을 모두 관리  end---------------

    public int removeStock(int count) {
        if( stockCount < count){
            throw new NotEnoughStockCountException("need more Stock");
        }
        stockCount -= count;
        return stockCount;
    }
}
