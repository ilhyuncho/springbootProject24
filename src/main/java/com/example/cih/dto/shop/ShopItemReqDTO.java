package com.example.cih.dto.shop;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
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
    private Integer originalPrice;
    private int stockCount;     // 재고수량

    private Long itemOptionId1;
    private Long itemOptionId2;
//    private String itemOption1;
//    private String itemOption2;

    @Min(value = 0, message = "멤버쉽 할인율은 최소 0 부터")
    @Max(value = 50, message = "멤버쉽 할인율은 최대 30 까지")
    private Integer membershipPercent;

    Boolean isEventTarget;

    private List<String> fileNames= new ArrayList<>();

}
