package com.example.cih.dto.shop;

import com.example.cih.domain.shop.ItemSellingStatus;
import com.example.cih.dto.ImageDTO;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ShopItemResDTO {
    private Long shopItemId;

    private String itemName;
    private Integer originalPrice;
    private int stockCount;     // 재고수량
    private String itemOption1;
    private String itemOption2;

    private Integer membershipPercent;
    private Boolean isEventTarget;

    private String itemDetail;
    private ItemSellingStatus itemSellStatus;
    private ImageDTO image;

    @Builder.Default
    private List<ImageDTO> fileNames= new ArrayList<>();

    @Builder(builderMethodName = "writeShopItemViewDTOBuilder")
    public ShopItemResDTO(Long shopItemId, String itemName, int originalPrice, int stockCount,
                          String itemOption1, String itemOption2, Integer membershipPercent,
                          Boolean isEventTarget, String itemDetail, ItemSellingStatus itemSellStatus,
                          ImageDTO image) {

        this.shopItemId = shopItemId;
        this.itemName = itemName;
        this.originalPrice = originalPrice;
        this.stockCount = stockCount;
        this.itemOption1 = itemOption1;
        this.itemOption2 = itemOption2;
        this.membershipPercent = membershipPercent;
        this.isEventTarget = isEventTarget;
        this.itemDetail = itemDetail;
        this.itemSellStatus = itemSellStatus;
        this.image = image;

    }

    public ImageDTO getMainImage(){
        return fileNames.stream().filter(image1 -> image1.getImageOrder() == 0)
                .findFirst().orElse(null);
    }
    public void addImage(String uuid, String fileName, int imageOrder){
        ImageDTO shopImageDTO = ImageDTO.builder()
                .uuid(uuid)
                .fileName(fileName)
                .imageOrder(imageOrder)
                .build();
        fileNames.add(shopImageDTO);
    }
}
