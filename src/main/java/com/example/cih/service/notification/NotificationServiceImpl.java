package com.example.cih.service.notification;

import com.example.cih.domain.notification.EventNotification;
import com.example.cih.domain.notification.NewsNotification;
import com.example.cih.domain.notification.NewsNotificationRepository;
import com.example.cih.dto.PageRequestDTO;
import com.example.cih.domain.notification.EventNotificationRepository;
import com.example.cih.dto.notification.NotificationDTO;
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
    private final ModelMapper modelMapper;

    @Override
    public List<NotificationDTO> readNotification(PageRequestDTO pageRequestDTO) {

        List<NewsNotification> newsNotifications = newsNotificationRepository.findAll();

        List<NotificationDTO> dtoList = newsNotifications.stream().map(noti -> modelMapper.map(noti, NotificationDTO.class))
                .collect(Collectors.toList());

        dtoList.stream().forEach( list -> log.error("readNotification: " + list));

        return dtoList;
    }

}
