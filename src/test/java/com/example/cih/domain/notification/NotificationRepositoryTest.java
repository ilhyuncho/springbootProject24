package com.example.cih.domain.notification;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class NotificationRepositoryTest {

    @Autowired
    NewsNotificationRepository newsNotificationRepository;

    @Test
    public void insertNoti(){

        IntStream.rangeClosed(1,100).forEach(i -> {

            NewsNotification noti = NewsNotification.builder()
                            .notiId(Long.valueOf(i))
                            .notiMessage("MEssage"+i)
                            .notiTarget("target"+i)
                            .build();

            NewsNotification result = newsNotificationRepository.save(noti);
            log.info("Noti Id: " + result.getNotiId());
        });
    }


}