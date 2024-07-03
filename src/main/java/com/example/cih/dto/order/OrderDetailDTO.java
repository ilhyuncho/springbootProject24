package com.example.cih.dto.order;


import com.example.cih.dto.shop.ItemOptionDTO;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class OrderDetailDTO{
    private Long cartId;
    private Long itemId;
    private Integer itemCount;
    private Integer itemPrice;
    private Integer discountPrice;
    private List<ItemOptionDTO> itemOptionList;  // 선택한 옵션 내용
}
