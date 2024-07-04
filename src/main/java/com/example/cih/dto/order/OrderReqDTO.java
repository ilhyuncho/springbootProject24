package com.example.cih.dto.order;


import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@ToString
public class OrderReqDTO {
    private Integer deliveryFee;
    private Integer totalPrice;
    private final List<OrderDetailDTO> listOrderDetail = new ArrayList<>();



}
