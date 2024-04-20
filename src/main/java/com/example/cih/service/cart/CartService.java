package com.example.cih.service.cart;

import com.example.cih.domain.cart.Cart;
import com.example.cih.domain.shop.Order;
import com.example.cih.dto.PageRequestDTO;
import com.example.cih.dto.PageResponseDTO;
import com.example.cih.dto.cart.CartDTO;


public interface CartService {

    Cart addCart(CartDTO cartDTO, String userName);
    PageResponseDTO<CartDTO> getCartAll(PageRequestDTO pageRequestDTO, String userName);
    Cart deleteInCart(Long cartId);
    void modify(CartDTO cartDTO);
}
