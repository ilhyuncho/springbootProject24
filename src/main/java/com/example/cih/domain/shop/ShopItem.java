package com.example.cih.domain.shop;


import com.example.cih.common.exception.NotEnoughStockCountException;
import com.example.cih.dto.ImageDTO;
import com.example.cih.dto.shop.ItemOptionDTO;
import lombok.*;
import lombok.experimental.SuperBuilder;
import lombok.extern.log4j.Log4j2;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.*;

@Entity
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="shopItems")
@Log4j2
public class ShopItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="SHOP_ITEM_ID")
    private Long shopItemId;

    private String itemName;
    private String itemTitle;
    private String itemDesc;

    private int stockCount;                   // 재고 수량
    private int purchaseCount;                // 구매 수량
    private boolean isFreeDelivery;           // 무료 배송 여부

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

    public void updateInfo(String itemName, int stockCount){
        this.itemName = itemName;
        this.stockCount = stockCount;
    }

    //ShopItem 엔티티 에서 ItemImage 엔티티 객체들을 모두 관리  begin---------------
    public void addImage(String uuid, String fileName, Boolean isMainImage){

        ItemImage carImage = ItemImage.builder()
                .uuid(uuid)
                .fileName(fileName)
                .shopItem(this)
                .imageOrder(itemImageSet.size())
                .isMainImage(isMainImage)
                .build();

        log.error("addImage : " + carImage);

        itemImageSet.add(carImage);
    }

    public void clearImages(){
        itemImageSet.forEach(image -> image.changeItem(null));
        this.itemImageSet.clear();
    }
    public void updateImages(List<String> listFileNames, String mainImageFileName){
        // 초기화
        clearImages();

        if(listFileNames.size() > 0){
            listFileNames.forEach(fileName -> {

                String[] index = fileName.split("_");
                addImage(index[0], index[1], (index[1].equals(mainImageFileName)) );
            });
        }

        itemImageSet.forEach(log::error);
    }
    //ShopItem 엔티티 에서 ItemImage 엔티티 객체들을 모두 관리  end---------------

    //ShopItem 엔티티 에서 ItemOption 엔티티 객체들을 모두 관리  begin---------------
    public void addItemOption(ItemOption itemOption){
        if( itemOption != null ){
            itemOptionSet.add(itemOption);
            itemOption.changeItem(this);
        }
    }

    public void clearItemOption(){
        itemOptionSet.forEach(option -> option.changeItem(null));
        this.itemOptionSet.clear();
    }

    public void updateItemOption(List<ItemOptionDTO> listItemOption){
        // 초기화
        clearItemOption();

        listItemOption.forEach(itemOptionDTO -> {
            String[] values = itemOptionDTO.getOptionValue().split(",");
            int orderIndex = 0;
            for (String value : values) {

                ItemOption itemOption = ItemOption.builder()
                        .itemOptionType(ItemOptionType.fromValue(Integer.valueOf(itemOptionDTO.getOptionType())))
                        .optionOrder(orderIndex++)
                        .typePriority(itemOptionDTO.getTypePriority())
                        .optionName(value.trim())
                        .shopItem(this)
                        .build();

                addItemOption(itemOption);
            }
        });
    }
    //ShopItem 엔티티 에서 ItemOption 엔티티 객체들을 모두 관리  end---------------

    public void addPurchaseCount(int count) {
        if(stockCount - purchaseCount < count){
            throw new NotEnoughStockCountException("need more Stock");
        }
        purchaseCount += count;
    }

    public SortedMap<ItemOptionType, String> getMapItemOption(){

        SortedMap<ItemOptionType, String> sortedMap = new TreeMap<>(Comparator.comparing(ItemOptionType::getType));
        //Map<ItemOptionType, String> map = new HashMap<>();

        itemOptionSet.stream()
                .sorted(Comparator.comparing(ItemOption::getTypePriority).thenComparing(ItemOption::getOptionOrder))
                .forEach(itemOption -> {
                    // 예) "10-흰색, 8-파랑색, 7-검은색, 11-빨강색"  형식으로 파싱
                    sortedMap.compute(itemOption.getItemOptionType(), (k, v) -> (v == null)
                            ? itemOption.getItemOptionId() + "-" + itemOption.getOptionName()
                            : (v += ", " + itemOption.getItemOptionId() + "-" + itemOption.getOptionName()));
                });

        return sortedMap;
    }
    public Map<ItemOptionType, String> getMapItemOptionForView(){
        Map<ItemOptionType, String> map = new HashMap<>();

        itemOptionSet.stream()
                .sorted(Comparator.comparing(ItemOption::getTypePriority).thenComparing(ItemOption::getOptionOrder))
                .forEach(itemOption -> {
                    // 예) "흰색, 파랑색, 검은색, 빨강색"  형식으로 파싱
                    map.compute(itemOption.getItemOptionType(), (k, v) -> (v == null)
                            ? itemOption.getOptionName()
                            : (v += ", " +itemOption.getOptionName()));
                });

        return map;
    }

    public ImageDTO getMainImageDTO(){
        ItemImage itemImage = itemImageSet.stream()
                //.filter(shopItemImage -> shopItemImage.getImageOrder() == 0)
                .filter(ItemImage::getIsMainImage)
                .findFirst().orElse(null);

        if(itemImage == null){
            log.error("itemImage == null");
            return null;
        }

        return ImageDTO.builder()
                .uuid(itemImage.getUuid())
                .fileName(itemImage.getFileName())
                .imageOrder(itemImage.getImageOrder())
                .build();
    }
}
