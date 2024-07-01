package com.example.cih.dto.shop;

import com.example.cih.domain.shop.ItemSellingStatus;
import com.example.cih.dto.ImageDTO;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ShopItemExtandDTO extends ShopItemDTO {
    private Integer stockCount;     // 재고수량
    private Integer membershipPercent;
    private Boolean isEventTarget;

    @Builder.Default
    private List<ItemOptionDTO> listOptionType= new ArrayList<>();

    @Builder.Default
    private List<ImageDTO> fileNames= new ArrayList<>();

    public void addImage(String uuid, String fileName, int imageOrder){
        ImageDTO shopImageDTO = ImageDTO.builder()
                .uuid(uuid)
                .fileName(fileName)
                .imageOrder(imageOrder)
                .build();
        fileNames.add(shopImageDTO);
    }

    public ItemOptionDTO getOptionType(Integer index){
        return listOptionType.get(index);
    }

    public ItemSellingStatus getSellingStatus(){
        return this.stockCount <= 0 ? ItemSellingStatus.STATUS_SOLDOUT : ItemSellingStatus.STATUS_SELLING;
    }
}
