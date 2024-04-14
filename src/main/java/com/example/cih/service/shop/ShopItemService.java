package com.example.cih.service.shop;

import com.example.cih.domain.shop.ShopItem;

public interface ShopItemService {

    //UserDTO findByUserName(String userName);
    ShopItem findOne(Long itemId);
}
