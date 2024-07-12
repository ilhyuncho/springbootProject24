package com.example.cih.dto.order;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDeliveryResDTO {
    private Long orderId;
    private String deliveryStatus;

    private String deliveryName;
    private String recipientName;
    private String recipientPhoneNumber;
    private String deliveryRequest;
    private String fullAddress;

}
