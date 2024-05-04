package com.example.cih.dto.shop;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ShopItemViewDTO {
    private Long shopItemId;

    private String itemName;
    private int price;
    private int stockCount;     // 재고수량
    private String itemOption1;
    private String itemOption2;
    @Builder.Default
    private List<ShopImageDTO> fileNames= new ArrayList<>();

    @Builder(builderMethodName = "writeShopItemViewDTOBuilder")
    public ShopItemViewDTO(Long shopItemId, String itemName, int price, int stockCount,
                           String itemOption1, String itemOption2) {

        this.shopItemId = shopItemId;
        this.itemName = itemName;
        this.price = price;
        this.stockCount = stockCount;
        this.itemOption1 = itemOption1;
        this.itemOption2 = itemOption2;
    }


    public void addImage(String uuid, String fileName, int imageOrder){
        ShopImageDTO shopImageDTO = ShopImageDTO.builder()
                .uuid(uuid)
                .fileName(fileName)
                .imageOrder(imageOrder)
                .build();
        fileNames.add(shopImageDTO);
    }
}
