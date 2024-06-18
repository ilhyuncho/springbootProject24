package com.example.cih.dto.shop;

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
public class ShopItemViewDTO {
    private Long shopItemId;

    private String itemName;
    private Integer originalPrice;
    private int stockCount;     // 재고수량
    private String itemOption1;
    private String itemOption2;

    private Integer membershipPercent;
    private Integer salePercent;
    private String saleStartDate;
    private String saleEndDate;

    @Builder.Default
    private List<ImageDTO> fileNames= new ArrayList<>();

    @Builder(builderMethodName = "writeShopItemViewDTOBuilder")
    public ShopItemViewDTO(Long shopItemId, String itemName, int originalPrice, int stockCount,
                           String itemOption1, String itemOption2, Integer membershipPercent,
                           Integer salePercent,String saleStartDate, String saleEndDate) {

        this.shopItemId = shopItemId;
        this.itemName = itemName;
        this.originalPrice = originalPrice;
        this.stockCount = stockCount;
        this.itemOption1 = itemOption1;
        this.itemOption2 = itemOption2;
        this.membershipPercent = membershipPercent;
        this.salePercent = salePercent;
        this.saleStartDate = saleStartDate;
        this.saleEndDate = saleEndDate;
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
