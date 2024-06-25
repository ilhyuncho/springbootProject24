package com.example.cih.service.shop;
import com.example.cih.dto.shop.ShopItemResDTO;
import com.example.cih.dto.shop.ShopItemSimpleDTO;
import com.example.cih.dto.shop.ShopItemReqDTO;
import com.example.cih.dto.shop.ShopItemViewDTO;

import java.util.List;

public interface ShopItemService {

    ShopItemViewDTO findItem(Long itemId);
    ShopItemResDTO findItemTemp(Long itemId);

    List<ShopItemViewDTO> getAllItems();
    List<ShopItemSimpleDTO> getAllItemsForShop();

    Long registerItem(ShopItemReqDTO shopItemReqDTO);

    void modifyItem(ShopItemReqDTO shopItemReqDTO);

    void deleteItem(Long itemId);
}
