package com.example.cih.domain.shop;


import com.example.cih.domain.notification.EventNotification;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class ShopItemRepositoryTests {

    @Autowired
    ShopItemRepository shopItemRepository;


    @Test
    @Transactional
    @Commit
    public void insertShopItem(){

        IntStream.rangeClosed(1,2).forEach(i -> {

            ShopItem shopItem = ShopItem.builder()
                    .itemName("item" + i)
                  //  .price(1000 * i)
                    .stockCount(10000)
                    .build();

            shopItem.addImage("1a1a1a", "ionic5.png");
            shopItem.addImage("2a2a2a", "ionic51.png");

            shopItem.addItemOption("option1", "option2");

            shopItemRepository.save(shopItem);
        });




    }
}
