package com.example.cih.dto.shop;

import lombok.*;

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
    private String itemOption1;
    private String itemOption2;

    private Integer membershipPercent;
    private Integer salePercent;
    private String saleStartDate;
    private String saleEndDate;

    private List<String> fileNames= new ArrayList<>();

}
