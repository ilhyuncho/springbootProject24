package com.example.cih.dto.order;


import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class OrderCancelDTO {
    private Long orderId;
}
