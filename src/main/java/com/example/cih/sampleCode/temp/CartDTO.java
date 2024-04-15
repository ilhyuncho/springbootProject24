package com.example.cih.sampleCode.temp;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartDTO {
    private Long orderId;
    private int orderStatus;
    private int orderCount;
    private Long shopItemId;
    private String itemName;
    private int itemPrice;
}
