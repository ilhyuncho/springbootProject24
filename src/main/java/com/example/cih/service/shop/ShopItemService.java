package com.example.cih.service.shop;
import com.example.cih.dto.shop.*;

import java.util.List;

public interface ShopItemService {

    ShopItemExtandDTO getItem(Long itemId);

    List<ShopItemExtandDTO> getAllItems();
    List<ShopItemDTO> getAllItemsForShop();

    Long registerItem(ShopItemReqDTO shopItemReqDTO);

    void modifyItem(ShopItemReqDTO shopItemReqDTO);

    void deleteItem(Long itemId);
}
