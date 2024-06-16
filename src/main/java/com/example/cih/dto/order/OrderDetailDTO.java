package com.example.cih.dto.order;


import lombok.*;

import java.io.Serializable;

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
}
