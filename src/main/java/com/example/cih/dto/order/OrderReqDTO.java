package com.example.cih.dto.order;


import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class OrderReqDTO {
    private Integer deliveryFee;
    private List<OrderDetailDTO> listOrderDetail = new ArrayList<>();

    @Builder
    public OrderReqDTO(Integer deliveryFee, List<OrderDetailDTO> listOrderDetail) {
        this.deliveryFee = deliveryFee;
        this.listOrderDetail = listOrderDetail;
    }
}
