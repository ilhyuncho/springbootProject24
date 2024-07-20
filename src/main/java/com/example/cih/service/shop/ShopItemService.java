package com.example.cih.service.shop;
import com.example.cih.domain.user.User;
import com.example.cih.dto.shop.*;

import java.util.List;

public interface ShopItemService {

    ShopItemExtandDTO getItemInfo(Long itemId, User user);

    List<ShopItemExtandDTO> getAllItems();
    List<ShopItemResDTO> getAllItemsForShop();

    Long registerItem(ShopItemReqDTO shopItemReqDTO);

    void modifyItem(ShopItemReqDTO shopItemReqDTO);

    void deleteItem(Long itemId);
}
