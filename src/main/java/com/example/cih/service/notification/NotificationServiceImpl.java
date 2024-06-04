package com.example.cih.service.notification;

import com.example.cih.common.exception.ItemNotFoundException;
import com.example.cih.common.util.Util;
import com.example.cih.domain.notification.*;
import com.example.cih.dto.PageRequestDTO;
import com.example.cih.dto.notification.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class NotificationServiceImpl implements NotificationService {

    private final NewsNotificationRepository newsNotificationRepository;
    private final EventNotificationRepository eventNotificationRepository;
    private final NotificationRepository notificationRepository;

    private final ModelMapper modelMapper;

    @Override
    public List<NotiEventResDTO> getListEventInfo(PageRequestDTO pageRequestDTO) {

        List<EventNotification> eventNotification = eventNotificationRepository.findAll();

        List<NotiEventResDTO> dtoList = eventNotification
                .stream().map(noti -> {

                    return NotiEventResDTO.builder()
                            .notiId(noti.getNotiId())
                            .name(noti.getName())
                            .title(noti.getTitle())
                            .message(noti.getMessage())
                            .regDate(Util.convertLocalDate(noti.getRegDate()))
                            .expiredDate(noti.getExpiredDate())
                            .build();
                        })
                .collect(Collectors.toList());

        dtoList.forEach(list -> log.error("getListEventInfo: " + list));

        return dtoList;
    }

    @Override
    public List<NotiNewsResDTO> getListNewsInfo(PageRequestDTO pageRequestDTO) {

        List<NewsNotification> newsNotifications = newsNotificationRepository.findAll();

        List<NotiNewsResDTO> dtoList = newsNotifications
                .stream().map(noti -> {

                    return NotiNewsResDTO.builder()
                            .notiId(noti.getNotiId())
                            .name(noti.getName())
                            .title(noti.getTitle())
                            .message(noti.getMessage())
                            .regDate(Util.convertLocalDate(noti.getRegDate()))
                            .target(noti.getTarget())
                            .build();
                })
                .collect(Collectors.toList());

        dtoList.forEach(list -> log.error("getListNewsInfo: " + list));

        return dtoList;
    }

    @Override
    public NotiEventResDTO getEventInfo(Long notiId) {

        Optional<EventNotification> result = eventNotificationRepository.findById(notiId);

       if( result.isPresent()){
           EventNotification eventNotification = result.get();

           return (NotiEventResDTO)entityToNotiResDTO(eventNotification);
       }
       log.error("getEventInfo() EventNotification is null!!!");
        return null;
    }

    @Override
    public NotiNewsResDTO getNewsInfo(Long notiId) {

        Optional<NewsNotification> result = newsNotificationRepository.findById(notiId);

        if( result.isPresent()){
            NewsNotification newsNotification = result.get();

            return (NotiNewsResDTO)entityToNotiResDTO(newsNotification);
        }
        log.error("getNewsInfo() NewsNotification is null!!!");
        return null;
    }

    @Override
    public NotiResDTO getNotiInfo(Long notiId) {

        Optional<Notification> result = notificationRepository.findById(notiId);

        NotiResDTO notiNewsResDTO = entityToNotiResDTO(result.get());

        return notiNewsResDTO;
    }

    @Override
    public Long registerEventNotification(NotificationRegDTO notificationRegDTO) {

        eventNotificationRepository.findByName(notificationRegDTO.getName())
                .ifPresent(m -> {
                    throw new ItemNotFoundException("해당 이벤트 정보가 이미 존재 함");
                });

        EventNotification eventNotification = dtoToEventEntity(notificationRegDTO);

        if(notificationRegDTO.getFileNames() != null){
            addFileNames(notificationRegDTO, eventNotification);
        }

        EventNotification saveItem = eventNotificationRepository.save(eventNotification);

        return saveItem.getNotiId();
    }
    @Override
    public Long registerNewsNotification(NotificationRegDTO notificationRegDTO) {

        newsNotificationRepository.findByName(notificationRegDTO.getName())
                .ifPresent(m -> {
                    throw new ItemNotFoundException("해당 이벤트 정보가 이미 존재 함");
                });

        NewsNotification newsNotification = dtoToNewsEntity(notificationRegDTO);

        if(notificationRegDTO.getFileNames() != null){
            addFileNames(notificationRegDTO, newsNotification);
        }

        NewsNotification saveItem = newsNotificationRepository.save(newsNotification);

        return saveItem.getNotiId();
    }
    private NotiResDTO entityToNotiResDTO(Notification notification) {

        NotiResDTO notiResDTO;
        if( notification.getClass().equals(NewsNotification.class)){
            NewsNotification noti = (NewsNotification)notification;

            NotiNewsResDTO notiNewsResDTO = modelMapper.map(noti, NotiNewsResDTO.class);
            log.error("NotiNewsResDTO : " + notiNewsResDTO);
            notiResDTO = notiNewsResDTO;
        }
        else{
            EventNotification noti = (EventNotification)notification;

            NotiEventResDTO notiEventResDTO = modelMapper.map(noti, NotiEventResDTO.class);
            log.error("NotiEventResDTO : " + notiEventResDTO);
            notiResDTO = notiEventResDTO;
        }
        // 이미지 파일 정보 매핑
        notification.getNotificationImageSet().forEach(image -> {
            notiResDTO.addImage(image.getUuid(), image.getFileName(), image.getImageOrder());
        });

        return notiResDTO;
    }

    public void addFileNames(NotificationRegDTO notificationRegDTO, Notification notification){

        notificationRegDTO.getFileNames().forEach(fileName ->{
            String[] arr = fileName.split("_");
            notification.addImage(arr[0], arr[1]);
        });
    }
    private static EventNotification dtoToEventEntity(NotificationRegDTO notificationRegDTO) {

        return EventNotification.builder()
                .name(notificationRegDTO.getName())
                .title(notificationRegDTO.getTitle())
                .message(notificationRegDTO.getMessage())
                .regDate(LocalDateTime.now())
                .expiredDate(LocalDate.parse(notificationRegDTO.getExpiredDate()))
                .build();
    }
    private static NewsNotification dtoToNewsEntity(NotificationRegDTO notificationRegDTO) {

        return NewsNotification.builder()
                .name(notificationRegDTO.getName())
                .title(notificationRegDTO.getTitle())
                .message(notificationRegDTO.getMessage())
                .regDate(LocalDateTime.now())
                .target("targetTemp")
                .build();
    }
}
