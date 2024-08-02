package com.example.cih;

import com.example.cih.service.shop.ShopItemServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@Log4j2
@SpringBootApplication
public class SpringbootProject24Application {

    public static void main(String[] args) {

        ConfigurableApplicationContext run = SpringApplication.run(SpringbootProject24Application.class, args);

        // 프로토타입 스코프 빈 테스트용 ( shopItemService 가 각각 생성되어 전달 됨
        ShopItemServiceImpl shopItemService = run.getBean( ShopItemServiceImpl.class);
        ShopItemServiceImpl shopItemService1 = run.getBean( ShopItemServiceImpl.class);

        Boolean isSame = shopItemService == shopItemService1;
        log.error(isSame);      // 결과는 false

    }

}
