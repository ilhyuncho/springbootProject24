package com.example.cih.dto.shop;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ShopItemResDTO {
    private Long shopItemId;
    private String itemName;
    private String itemTitle;
    private String itemDesc;
    private Integer originalPrice;
    private Integer discountPrice;
    private Boolean isFreeDelivery;     // 무료 배송 여부
}
