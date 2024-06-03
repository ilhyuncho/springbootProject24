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
import java.time.LocalDate;
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
    private final ModelMapper modelMapper;

    @Override
    public List<NotificationResDTO> getListEventInfo(PageRequestDTO pageRequestDTO) {

        List<EventNotification> eventNotification = eventNotificationRepository.findAll();

        List<NotificationResDTO> dtoList = eventNotification
                .stream().map(noti -> {
                    return NotificationResDTO.builder()
                            .notiId(noti.getNotiId())
                            .name(noti.getName())
                            .title(noti.getTitle())
                            .message(noti.getMessage())
                            .expiredDate(noti.getExpiredDate())
                            .build();
                        })
                .collect(Collectors.toList());

        dtoList.forEach(list -> log.error("readEventNotification: " + list));

        return dtoList;
    }

    @Override
    public List<NotificationResDTO> getListNewsInfo(PageRequestDTO pageRequestDTO) {

        List<NewsNotification> newsNotifications = newsNotificationRepository.findAll();

        List<NotificationResDTO> dtoList = newsNotifications
                .stream().map(noti -> modelMapper.map(noti, NotificationResDTO.class))
                .collect(Collectors.toList());

        dtoList.forEach(list -> log.error("readNewsNotification: " + list));

        return dtoList;
    }

    @Override
    public NotificationResDTO getEventInfo(Long notiId) {

        Optional<EventNotification> result = eventNotificationRepository.findById(notiId);

       if( result.isPresent()){
           EventNotification eventNotification = result.get();

           return entityToDTO(eventNotification);
       }

        return null;
    }

    private static NotificationResDTO entityToDTO(EventNotification eventNotification) {
        NotificationResDTO notificationResDTO = NotificationResDTO.builder()
                .notiId(eventNotification.getNotiId())
                .title(eventNotification.getTitle())
                .name(eventNotification.getName())
                .message(eventNotification.getMessage())
                .expiredDate(eventNotification.getExpiredDate())
                .build();

        // 이미지 파일 정보 매핑
        eventNotification.getNotificationImageSet().forEach(image -> {
            notificationResDTO.addImage(image.getUuid(), image.getFileName(), image.getImageOrder());
        });

        return notificationResDTO;
    }

    @Override
    public Long registerNotification(NotificationRegDTO notificationRegDTO) {

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
                .expiredDate(LocalDate.parse(notificationRegDTO.getExpiredDate()))
                .build();
    }
    private static NewsNotification dtoToNewsEntity(NotificationRegDTO notificationRegDTO) {

        return NewsNotification.builder()
                .name(notificationRegDTO.getName())
                .title(notificationRegDTO.getTitle())
                .message(notificationRegDTO.getMessage())
                .target("targetTemp")
                .build();
    }

}
