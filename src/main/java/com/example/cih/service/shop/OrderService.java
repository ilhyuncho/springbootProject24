package com.example.cih.service.shop;

import com.example.cih.domain.user.User;
import com.example.cih.dto.order.*;
import com.example.cih.dto.shop.ItemBuyReqDTO;
import com.example.cih.dto.PageRequestDTO;
import com.example.cih.dto.PageResponseDTO;

import java.util.List;

public interface OrderService {
    Long createOrder(User user, OrderReqDTO orderReqDTO);
    void cancelOrder(Long orderId);
    PageResponseDTO<OrderListResDTO> getOrderAll(User user, PageRequestDTO pageRequestDTO);
    List<OrderItemResDTO> getOrderDetail(Long orderId);
    OrderTemporaryResDTO getOrderTemporary(Long orderTemporaryId);
    Long addOrderTemporary(ItemBuyReqDTO itemBuyReqDTO, User user);
    OrderDeliveryResDTO getOrderDeliveryProcess(Long OrderId);


}
