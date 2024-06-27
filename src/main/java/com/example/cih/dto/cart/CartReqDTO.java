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

    // 임시로
    private List<ItemOptionDTO> itemOptionList;
    private Long itemOptionId1;
    private Long itemOptionId2;

    public Long getItemOptionId1() {
        return itemOptionId1 == null ? 0L : itemOptionId1;
    }
    public Long getItemOptionId2() {
        return itemOptionId2 == null ? 0L : itemOptionId2;
    }
}
