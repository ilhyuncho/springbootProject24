package com.example.cih.common.runner;

import com.example.cih.common.systemLog.SystemLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;


@Log4j2
@Component
@RequiredArgsConstructor
@Order(1)       // 낮을수록 우선 순위 높단
public class CustomApplicationRunner implements ApplicationRunner {

    private final SystemLogService systemLogService;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        log.error("CustomApplicationRunner(Order=1 ) : run~~ ");
        Arrays.stream(args.getSourceArgs()).forEach(log::error);
        // 서버 실행 시간 DB 저장
        ServerExecTimeLog();
    }

    public void ServerExecTimeLog(){

        systemLogService.systemLog("서버 로딩");
    }
}
