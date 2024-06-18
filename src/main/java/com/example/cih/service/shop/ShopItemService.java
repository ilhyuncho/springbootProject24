package com.example.cih.service.shop;
import com.example.cih.dto.shop.ShopItemReqDTO;
import com.example.cih.dto.shop.ShopItemViewDTO;

import java.util.List;

public interface ShopItemService {

    //UserDTO findByUserName(String userName);
    ShopItemViewDTO findOne(Long itemId);

    List<ShopItemViewDTO> getAllItems();

    Long registerItem(ShopItemReqDTO shopItemReqDTO);

    void modifyItem(ShopItemReqDTO shopItemReqDTO);

    void deleteItem(Long itemId);
}
