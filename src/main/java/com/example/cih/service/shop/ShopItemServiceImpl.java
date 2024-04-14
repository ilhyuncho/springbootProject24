package com.example.cih.service.shop;

import com.example.cih.domain.shop.ShopItem;
import com.example.cih.domain.shop.ShopItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;


@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class ShopItemServiceImpl implements ShopItemService {

    private final ShopItemRepository shopItemRepository;

    @Override
    public ShopItem findOne(Long itemId) {

        Optional<ShopItem> byId = shopItemRepository.findById(itemId);
        return byId.orElse(null);
    }

}
