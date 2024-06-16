package com.example.cih.dto.order;


import lombok.*;

import java.io.Serializable;

@Getter
@NoArgsConstructor
@ToString
public class OrderDetailDTO{
    private Long itemId;
    private Integer itemCount;
    private Integer itemPrice;

    @Builder
    public OrderDetailDTO(Long itemId, Integer itemCount, Integer itemPrice) {
        this.itemId = itemId;
        this.itemCount = itemCount;
        this.itemPrice = itemPrice;
    }

}
