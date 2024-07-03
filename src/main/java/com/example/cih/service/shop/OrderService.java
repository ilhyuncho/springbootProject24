package com.example.cih.service.shop;

import com.example.cih.dto.order.OrderItemResDTO;
import com.example.cih.dto.order.OrderReqDTO;
import com.example.cih.dto.order.OrderViewDTO;
import com.example.cih.dto.PageRequestDTO;
import com.example.cih.dto.PageResponseDTO;

public interface OrderService {
    Long createOrder(String userName, OrderReqDTO orderReqDTO );
    PageResponseDTO<OrderItemResDTO> getOrderAll(PageRequestDTO pageRequestDTO, String userName);
    OrderViewDTO getOrderDetail(Long orderId);
    void cancelOrder(Long orderId);
}
