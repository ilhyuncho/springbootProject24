package com.example.cih.service.shop;

import com.example.cih.domain.shop.ShopItem;
import com.example.cih.dto.shop.ShopItemDTO;

import java.util.List;

public interface ShopItemService {

    //UserDTO findByUserName(String userName);
    ShopItem findOne(Long itemId);

    List<ShopItemDTO> getAllItems();

    Long registerItem(ShopItemDTO shopItemDTO);
}
