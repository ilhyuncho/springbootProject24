package com.example.cih.common.systemLog;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class SystemLogServiceImpl implements SystemLogService {

    private final SystemLogRepository systemLogRepository;

    public void systemLog(String... logMessages ){

        SystemLog systemLog = SystemLog.builder()
                .text1(logMessages[0])
                .text2(logMessages.length > 1 ? logMessages[1] : "")
                .build();
        systemLogRepository.save(systemLog);
    }

}
