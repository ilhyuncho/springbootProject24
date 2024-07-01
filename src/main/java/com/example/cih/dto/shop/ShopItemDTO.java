package com.example.cih.dto.shop;

import com.example.cih.dto.ImageDTO;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ShopItemDTO {
    private Long shopItemId;
    private String itemName;
    private String itemTitle;
    private String itemDesc;
    private Integer originalPrice;
    private Integer discountPrice;

    private ImageDTO mainImage; // html에서 responseDTO.getMainImage() 로 참조 함
}
