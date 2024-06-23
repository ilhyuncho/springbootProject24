package com.example.cih.dto.cart;


import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class CartDetailResDTO {
    private Long cartId;
    private Long shopItemId;
    private String itemName;
    private Integer itemCount;
    private Integer itemPrice;
    private Integer discountPrice;

    private String optionType1;
    private String optionName1;
    private String optionType2;
    private String optionName2;
}
