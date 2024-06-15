package com.example.cih.service.cart;

import com.example.cih.domain.cart.Cart;
import com.example.cih.dto.cart.CartDTO;
import com.example.cih.dto.cart.CartDetailResDTO;

import java.util.List;


public interface CartService {

    Cart addCart(CartDTO cartDTO, String userName);
    List<CartDetailResDTO> getCartAll(String userName);
    Cart deleteInCart(Long cartId);
    void modify(CartDTO cartDTO);
}
