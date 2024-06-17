package com.example.cih.dto.order;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderViewDTO {
    private Long orderId;
    private int deliveryStatus;
    private int orderCount;
    private Long shopItemId;
    private String itemName;
    private int itemPrice;
}
