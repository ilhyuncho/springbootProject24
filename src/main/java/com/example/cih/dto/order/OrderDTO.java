package com.example.cih.dto.order;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private Long orderId;
    private String deliveryStatus;
    private int orderCount;
    private Long shopItemId;
    private String itemName;
    private int itemPrice;
}
