package com.example.cih.service.cart;

import com.example.cih.domain.cart.Cart;
import com.example.cih.dto.cart.CartReqDTO;
import com.example.cih.dto.cart.CartDetailResDTO;

import java.util.List;


public interface CartService {

    List<CartDetailResDTO> getCartAll(String userName);
    void addCart(CartReqDTO cartReqDTO, String userName);
    void modify(CartReqDTO cartReqDTO);
    Cart deleteInCart(Long cartId);

}
