package com.example.cih.dto.shop;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ShopItemDTO {
    private Long shopItemId;
    private String itemName;
    private int price;
    private int stockCount;     // 재고수량
    private String itemOption1;
    private String itemOption2;
    private List<String> fileNames= new ArrayList<>();

}
