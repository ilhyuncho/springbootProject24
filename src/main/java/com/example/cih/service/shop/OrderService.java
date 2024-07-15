package com.example.cih.service.shop;

import com.example.cih.domain.user.User;
import com.example.cih.dto.cart.CartReqDTO;
import com.example.cih.dto.order.OrderDeliveryResDTO;
import com.example.cih.dto.order.OrderItemResDTO;
import com.example.cih.dto.order.OrderReqDTO;
import com.example.cih.dto.order.OrderViewDTO;
import com.example.cih.dto.PageRequestDTO;
import com.example.cih.dto.PageResponseDTO;

public interface OrderService {
    Long createOrder(User user, OrderReqDTO orderReqDTO);
    PageResponseDTO<OrderItemResDTO> getOrderAll(PageRequestDTO pageRequestDTO, String userName);
    OrderViewDTO getOrderDetail(Long orderId);
    void cancelOrder(Long orderId);
    Long addOrderTemporary(CartReqDTO cartReqDTO, User user);

    OrderDeliveryResDTO getOrderDeliveryProcess(Long OrderId);
}
