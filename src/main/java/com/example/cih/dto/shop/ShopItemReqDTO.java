package com.example.cih.dto.shop;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.List;

//@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class ShopItemReqDTO {

    private Long shopItemId;
    private String itemName;

    @Positive   // 양수
    private Integer originalPrice;

    @Positive    // 양수
    private int stockCount;     // 재고수량

    private int itemOptionType1;
    private String itemOptionValue1;
    private int itemOptionType2;
    private String itemOptionValue2;

    @Min(value = 0, message = "멤버쉽 할인율은 최소 0 부터")
    @Max(value = 50, message = "멤버쉽 할인율은 최대 50 까지")
    private Integer membershipPercent;

    Boolean isEventTarget;

    private List<String> fileNames= new ArrayList<>();

    public void setItemName(String itemName){
        this.itemName = itemName;
    }
}
