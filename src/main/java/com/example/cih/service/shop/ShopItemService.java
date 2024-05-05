package com.example.cih.service.shop;
import com.example.cih.dto.shop.ShopItemDTO;
import com.example.cih.dto.shop.ShopItemViewDTO;

import java.util.List;

public interface ShopItemService {

    //UserDTO findByUserName(String userName);
    ShopItemViewDTO findOne(Long itemId);

    List<ShopItemViewDTO> getAllItems();

    Long registerItem(ShopItemDTO shopItemDTO);

    void modifyItem(ShopItemDTO shopItemDTO);

    void deleteItem(Long itemId);
}
