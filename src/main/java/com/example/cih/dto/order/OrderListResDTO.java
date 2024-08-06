package com.example.cih.dto.order;


import com.example.cih.dto.ImageListDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class OrderListResDTO extends ImageListDTO {
    private Long orderId;
    private String deliveryStatus;
    private String itemName;

    private int orderPrice;         // 총 주문 가격
    private int paymentPrice;       // 총 결제 가격
    private LocalDate orderDate;

}
