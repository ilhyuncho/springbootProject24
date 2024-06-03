package com.example.cih.service.notification;

import com.example.cih.common.exception.ItemNotFoundException;
import com.example.cih.domain.notification.*;
import com.example.cih.dto.PageRequestDTO;
import com.example.cih.dto.notification.NotificationRegDTO;
import com.example.cih.dto.notification.NotificationResDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
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

    @Override
    public Long registerNotification(NotificationRegDTO notificationRegDTO) {

        eventNotificationRepository.findByNotiName(notificationRegDTO.getEventName())
                .ifPresent(m -> {
                    throw new ItemNotFoundException("해당 이벤트 정보가 이미 존재 함");
                });

        EventNotification eventNotification = dtoToEntity(notificationRegDTO);

        EventNotification saveItem = eventNotificationRepository.save(eventNotification);

        return saveItem.getNotiId();
    }

    private static EventNotification dtoToEntity(NotificationRegDTO notificationRegDTO) {
        EventNotification eventNotification = EventNotification.builder()
                .notiName(notificationRegDTO.getEventName())
                .notiTitle(notificationRegDTO.getEventTitle())
                .notiMessage("fsdfsdf")
                .expiredDate(LocalDateTime.now())
                .build();

        if(notificationRegDTO.getFileNames() != null){
            notificationRegDTO.getFileNames().forEach(fileName ->{
                String[] arr = fileName.split("_");
                eventNotification.addImage(arr[0], arr[1]);
            });
        }

        return eventNotification;
    }

}
