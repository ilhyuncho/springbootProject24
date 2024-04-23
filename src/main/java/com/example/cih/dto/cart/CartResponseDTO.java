package com.example.cih.dto.cart;


import lombok.*;

@Getter
@ToString
@Builder
public class CartResponseDTO {
    private Long cartId;
    private Long shopItemId;
    private String itemName;
    private int itemCount;
    private String option1;
}
