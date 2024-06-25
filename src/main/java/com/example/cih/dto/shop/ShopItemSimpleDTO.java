package com.example.cih.dto.shop;

import com.example.cih.dto.ImageDTO;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ShopItemSimpleDTO {
    private Long shopItemId;
    private String itemName;
    private Integer originalPrice;
    private ImageDTO image;

    public void addImage(String uuid, String fileName, int imageOrder){
        ImageDTO shopImageDTO = ImageDTO.builder()
                .uuid(uuid)
                .fileName(fileName)
                .imageOrder(imageOrder)
                .build();

        image = shopImageDTO;
    }
}
