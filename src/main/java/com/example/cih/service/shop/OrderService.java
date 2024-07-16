package com.example.cih.service.shop;

import com.example.cih.domain.user.User;
import com.example.cih.dto.order.*;
import com.example.cih.dto.shop.ItemBuyReqDTO;
import com.example.cih.dto.PageRequestDTO;
import com.example.cih.dto.PageResponseDTO;

public interface OrderService {
    Long createOrder(User user, OrderReqDTO orderReqDTO);
    void cancelOrder(Long orderId);
    PageResponseDTO<OrderItemResDTO> getOrderAll(PageRequestDTO pageRequestDTO, String userName);
    OrderTemporaryResDTO getOrderTemporary(Long orderTemporaryId);

    Long addOrderTemporary(ItemBuyReqDTO itemBuyReqDTO, User user);
    OrderDeliveryResDTO getOrderDeliveryProcess(Long OrderId);

    OrderViewDTO getOrderDetail(Long orderId);
}
