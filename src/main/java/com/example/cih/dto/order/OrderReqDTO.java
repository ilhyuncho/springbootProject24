package com.example.cih.dto.order;


import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@ToString
public class OrderReqDTO {
    private Integer deliveryFee;        // 배송비
    private Integer totalPrice;         // 할인 적용 안한 총 상품 가격
    private Integer totalDiscountPrice;
    private Long userAddressBookId;
    private final List<OrderDetailDTO> listOrderDetail = new ArrayList<>();



}
