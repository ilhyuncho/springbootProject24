package com.example.cih;

import com.example.cih.service.shop.ShopItemServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;

@Log4j2
@SpringBootApplication
@ConfigurationPropertiesScan  // 스프링이 애플리케이션 콘텍스트에서 설정 데이터 빈을 찾는다
                              // @ConfigurationProperties가 추가된 클래스를 탐색한 후 스프링 빈으로 등록한다.
                              // 스프링 부트 2.2 버전 이후 등장
                              // or @EnableConfigurationProperties 를 통해 직접 설정 클래스를 지정할수 도 있다
                              // @EnableConfigurationProperties({TestConfigA.class, TestConfigB.class})
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
