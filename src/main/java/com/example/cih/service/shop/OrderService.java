package com.example.cih.service.shop;

import com.example.cih.domain.shop.Order;
import com.example.cih.dto.order.OrderDTO;
import com.example.cih.dto.order.OrderViewDTO;
import com.example.cih.dto.PageRequestDTO;
import com.example.cih.dto.PageResponseDTO;

public interface OrderService {

    Long order(String userName, Long itemId, int count);
    PageResponseDTO<OrderDTO> getOrderAll(PageRequestDTO pageRequestDTO, String userName);
    OrderViewDTO getOrderDetail(Long orderId);
    Order cancelOrder(Long orderId);
}
