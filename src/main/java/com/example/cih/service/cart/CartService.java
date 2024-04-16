package com.example.cih.service.cart;

import com.example.cih.domain.shop.Order;
import com.example.cih.dto.PageRequestDTO;
import com.example.cih.dto.PageResponseDTO;
import com.example.cih.dto.cart.CartDTO;


public interface CartService {
    PageResponseDTO<CartDTO> getCartAll(PageRequestDTO pageRequestDTO, String userName);
    Order deleteInCart(Long orderId) throws Exception;
}
