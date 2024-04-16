package com.example.cih.service.shop;

import com.example.cih.domain.shop.Order;
import com.example.cih.dto.order.OrderDTO;
import com.example.cih.dto.order.OrderDetailDTO;
import com.example.cih.dto.PageRequestDTO;
import com.example.cih.dto.PageResponseDTO;

public interface OrderService {

    Long order(String userName, Long itemId, int count) throws Exception;

    PageResponseDTO<OrderDTO> getOrderAll(PageRequestDTO pageRequestDTO, String userName);
    OrderDetailDTO getOrderDetail(Long orderId);
    Order cancelOrder(Long orderId) throws Exception;
}
