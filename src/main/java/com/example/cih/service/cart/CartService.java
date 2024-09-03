package com.example.cih.service.cart;

import com.example.cih.domain.cart.Cart;
import com.example.cih.domain.user.User;
import com.example.cih.dto.shop.ItemBuyReqDTO;
import com.example.cih.dto.cart.CartDetailResDTO;

import java.util.List;


public interface CartService {

    List<CartDetailResDTO> getCartAll(User user);
    Long addCart(ItemBuyReqDTO itemBuyReqDTO, User user);
    void modify(ItemBuyReqDTO itemBuyReqDTO);
    Cart deleteInCart(Long cartId);

}
