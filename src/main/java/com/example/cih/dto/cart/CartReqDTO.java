package com.example.cih.dto.cart;


import com.example.cih.dto.shop.ItemOptionDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartReqDTO {
    private Long cartId;
    private Long shopItemId;
    private String itemName;
    private Integer itemCount;
    private Integer itemPrice;
    
    private List<ItemOptionDTO> itemOptionList;  // 선택한 옵션 내용
}
