package com.example.cih.service.notification;

import com.example.cih.domain.notification.EventNotification;
import com.example.cih.domain.notification.EventNotificationRepository;
import com.example.cih.domain.notification.NewsNotification;
import com.example.cih.domain.notification.NewsNotificationRepository;
import com.example.cih.dto.PageRequestDTO;
import com.example.cih.dto.notification.NotificationResDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class NotificationServiceImpl implements NotificationService {

    private final NewsNotificationRepository newsNotificationRepository;
    private final EventNotificationRepository eventNotificationRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<NotificationResDTO> readEventNotification(PageRequestDTO pageRequestDTO) {

        List<EventNotification> eventNotification = eventNotificationRepository.findAll();

        List<NotificationResDTO> dtoList = eventNotification
                .stream().map(noti -> {
                    return NotificationResDTO.builder()
                            .notiId(noti.getNotiId())
                            .notiMessage(noti.getNotiMessage())
                            .expiredDate(noti.getExpiredDate())
                            .build();
                        })
                .collect(Collectors.toList());

        dtoList.stream().forEach( list -> log.error("readEventNotification: " + list));

        return dtoList;
    }

    @Override
    public List<NotificationResDTO> readNewsNotification(PageRequestDTO pageRequestDTO) {

        List<NewsNotification> newsNotifications = newsNotificationRepository.findAll();

        List<NotificationResDTO> dtoList = newsNotifications
                .stream().map(noti -> modelMapper.map(noti, NotificationResDTO.class))
                .collect(Collectors.toList());

        dtoList.stream().forEach( list -> log.error("readNewsNotification: " + list));

        return dtoList;
    }
}
