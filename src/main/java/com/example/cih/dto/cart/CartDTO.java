package com.example.cih.dto.cart;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartDTO {
    private Long cartId;
    private Long shopItemId;
    private String itemName;
    private int itemCount;
    private Long itemOption;
}
