package com.example.cih.domain.notification;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class NotificationRepositoryTest {

    @Autowired
    NewsNotificationRepository newsNotificationRepository;

    @Autowired
    EventNotificationRepository eventNotificationRepository;

    @Autowired
    NotificationRepository notificationRepository;


    @Test
    public void insertNoti(){

        IntStream.rangeClosed(1,100).forEach(i -> {

            NewsNotification noti = NewsNotification.builder()
                            .notiId(Long.valueOf(i))
                            .message("MEssage"+i)
                            .target("target"+i)
                            .build();

            NewsNotification result = newsNotificationRepository.save(noti);
            log.info("Noti Id: " + result.getNotiId());
        });
    }
    @Test
    public void insertNoti1(){

        IntStream.rangeClosed(1,100).forEach(i -> {

            EventNotification noti = EventNotification.builder()
                    .notiId(Long.valueOf(i))
                    .message("eventMEssage"+i)
                    .expiredDate(LocalDate.now())
                    .build();

            EventNotification result = eventNotificationRepository.save(noti);
            log.info("Noti Id: " + result.getNotiId());



        });


    }

    @Test
    public void selectNotiAll(){

        // NewsNotification, EventNotification 둘다 정보를 가져옴
        List all = notificationRepository.findAll();

        for (Object o : all) {
            if( o.getClass().equals(NewsNotification.class)){
                NewsNotification noti = (NewsNotification)o;
                log.error("news-" + noti.getNotiId() + "_" + noti.getMessage() + "_" +noti.getTarget());
            }
            else{
                EventNotification noti = (EventNotification)o;
                log.error("Event-" + noti.getNotiId() + "_" + noti.getMessage() + "_" +noti.getExpiredDate());
            }
        }

    }




}