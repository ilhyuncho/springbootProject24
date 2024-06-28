package com.example.cih.dto.shop;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ShopItemReqDTO {

    private Long shopItemId;
    private String itemName;

    @Positive   // 양수 체크
    private Integer originalPrice;
    @Positive   // 양수 체크
    private Integer discountPrice;

    @Positive    // 양수 체크
    private Integer stockCount;     // 재고수량

    private List<ItemOptionDTO> itemOptionList;

    @Min(value = 0, message = "멤버쉽 할인율은 최소 0 부터")
    @Max(value = 50, message = "멤버쉽 할인율은 최대 50 까지")
    private Integer membershipPercent;

    Boolean isEventTarget;

    private List<String> fileNames= new ArrayList<>();

    public void setItemName(String itemName){
        this.itemName = itemName;
    }
}
