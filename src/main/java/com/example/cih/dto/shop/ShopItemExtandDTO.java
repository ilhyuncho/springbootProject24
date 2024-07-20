package com.example.cih.dto.shop;

import com.example.cih.domain.shop.ItemSellingStatus;
import com.example.cih.dto.ImageDTO;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ShopItemExtandDTO extends ShopItemResDTO {
    private Integer stockCount;         // 재고수량
    private Integer purchaseCount;      // 구매수량
    private Integer membershipPercent;
    private Boolean isEventTarget;

    @Builder.Default
    private List<ItemOptionDTO> listOptionType= new ArrayList<>();

    private ImageDTO mainImage; // html에서 responseDTO.getMainImage() 로 참조 함

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

    public List<ImageDTO> getImageDTOExceptMainImage(){
        return fileNames.stream().filter(imageDTO -> !Objects.equals(imageDTO.getFileName(), mainImage.getFileName()))
                .collect(Collectors.toList());
    }
}
