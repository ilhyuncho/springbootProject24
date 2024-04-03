package com.example.cih.common.runner;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;


@Log4j2
@Component
@RequiredArgsConstructor
@Order(2)
public class CustomCommandLineRunner implements CommandLineRunner {
    // CommandLineRunner -> 애플리케이션을 시작할때 특정 코드를 실행해야 할때

    // 스프링 부트 애플리케이션이 빈 등록을 포함한 초기화 과정 수행을 거의 다 마친 뒤에 실행되므로
    // 어떤 빈이든 주입받아 사용할수 있다

    @Override
    public void run(String... args) throws Exception {

        log.error("CustomCommandLineRunner(Order=2) : run~~ ");
        Arrays.stream(args).forEach(log::error);
    }


}
