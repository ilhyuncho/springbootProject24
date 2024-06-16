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
    private List<OrderDetailDTO> listOrderDetail = new ArrayList<>();

    @Builder
    public OrderReqDTO(Integer deliveryFee, Integer totalPrice, List<OrderDetailDTO> listOrderDetail) {
        this.deliveryFee = deliveryFee;
        this.totalPrice = totalPrice;
        this.listOrderDetail = listOrderDetail;
    }

}
