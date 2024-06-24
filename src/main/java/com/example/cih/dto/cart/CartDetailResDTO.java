package com.example.cih.dto.cart;


import com.example.cih.domain.shop.ItemOption;
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

    public void setOptionType1(ItemOption itemOption){
        this.optionType1 = itemOption.getType().getName();
        this.optionName1 = itemOption.getOption1();
    }
    public void setOptionType2(ItemOption itemOption){
        this.optionType2 = itemOption.getType().getName();
        this.optionName2 = itemOption.getOption1();
    }

}
