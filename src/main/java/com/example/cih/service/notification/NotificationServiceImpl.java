package com.example.cih.service.notification;

import com.example.cih.common.exception.ItemNotFoundException;
import com.example.cih.common.util.Util;
import com.example.cih.domain.notification.*;
import com.example.cih.dto.PageRequestDTO;
import com.example.cih.dto.PageResponseDTO;
import com.example.cih.dto.notification.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
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
    public PageResponseDTO<NotiEventResDTO> getListEventInfo(PageRequestDTO pageRequestDTO) {

        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable("regDate");

        Page<EventNotification> result = eventNotificationRepository.searchAll(types, keyword, pageable);
        List<EventNotification> eventNotification = result.getContent();


        List<NotiEventResDTO> dtoList = eventNotification
                .stream()
                .map(noti -> {

                    return NotiEventResDTO.builder()
                            .notiId(noti.getNotiId())
                            .name(noti.getName())
                            .title(noti.getTitle())
                            .message(noti.getMessage())
                            .regDate(Util.convertLocalDate(noti.getRegDate()))
                            .eventStartTime(noti.getEventStartTime())
                            .eventEndTime(noti.getEventEndTime())
                            .isUse(noti.getIsUse())
                            .isPopup(noti.getIsPopup())
                            .build();
                })
                .collect(Collectors.toList());

        dtoList.forEach(list -> log.error("getListEventInfo: " + list));



        return PageResponseDTO.<NotiEventResDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int)result.getTotalElements())
                .build();
    }

    @Override
    public PageResponseDTO<NotiNewsResDTO> getListNewsInfo(PageRequestDTO pageRequestDTO) {

        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable("regDate");

       // Page<NewsNotification> result = newsNotificationRepository.findAll();
        Page<NewsNotification> result = newsNotificationRepository.searchAll(types, keyword, pageable);
        List<NotiNewsResDTO> dtoList = result
                .stream().map(noti -> {

                    return NotiNewsResDTO.builder()
                            .notiId(noti.getNotiId())
                            .name(noti.getName())
                            .title(noti.getTitle())
                            .message(noti.getMessage())
                            .regDate(Util.convertLocalDate(noti.getRegDate()))
                            .target(noti.getTarget())
                            .isUse(noti.getIsUse())
                            .isPopup(noti.getIsPopup())
                            .build();
                })
                .collect(Collectors.toList());

        dtoList.forEach(list -> log.error("getListNewsInfo: " + list));

        return PageResponseDTO.<NotiNewsResDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int)result.getTotalElements())
                .build();
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

        log.error("notiNewsResDTO : " + notiNewsResDTO);
        return notiNewsResDTO;
    }

    @Override
    public Long registerEventNotification(NotificationRegDTO notificationRegDTO) {

        log.error("notificationRegDTO : " + notificationRegDTO);

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

    @Override
    public void modifyEventNotification(Long notiId, NotificationRegDTO dto) {

        EventNotification eventNotification = eventNotificationRepository.findById(notiId)
                .orElseThrow(() -> new NoSuchElementException("해당 이벤트 정보가 존재하지않습니다"));

//        eventNotification.changeInfo(dto.getName()
//                                    , dto.getTitle()
//                                    , dto.getMessage()
//                                    , dto.getIsUse()
//                                    ,dto.getIsPopup());

        eventNotification.changeEventTime(Util.convertStringToLocalDateTime(dto.getEventStartTime())
        , Util.convertStringToLocalDateTime(dto.getEventEndTime()));

        log.error("modifyEventNotification() eventNotification : " + eventNotification);
        EventNotification save = eventNotificationRepository.save(eventNotification);
        log.error("modifyEventNotification() save : " + save);

//        Optional<Notification> result = notificationRepository.findById(notiId);
//
//        if( result.isPresent()){
//            Notification notification = result.get();
//
//            notification.changeInfo(dto.getName()
//                    , dto.getTitle()
//                    , dto.getMessage()
//                    , dto.getIsUse()
//                    ,dto.getIsPopup());
//
//            notificationRepository.save(notification);
//        }
//        else{
//            log.error("gsdgdfgdfgdfg");
//        }


    }

    @Override
    public NotiEventResDTO getRandomPopupEventInfo() {

        EventNotification eventNotification = eventNotificationRepository.searchTodayRandomEvent();

        NotiEventResDTO notiEventResDTO = modelMapper.map(eventNotification, NotiEventResDTO.class);

        return notiEventResDTO;
    }

    private NotiResDTO entityToNotiResDTO(Notification notification) {

        NotiResDTO notiResDTO;
        if( notification.getClass().equals(NewsNotification.class)){
            NewsNotification noti = (NewsNotification)notification;

            NotiNewsResDTO notiNewsResDTO = modelMapper.map(noti, NotiNewsResDTO.class);

            notiResDTO = notiNewsResDTO;
        }
        else{
            EventNotification noti = (EventNotification)notification;

            NotiEventResDTO notiEventResDTO = modelMapper.map(noti, NotiEventResDTO.class);

            notiEventResDTO.setEventStartDate(notiEventResDTO.getEventStartTime().toLocalDate());
            notiEventResDTO.setEventEndDate(notiEventResDTO.getEventEndTime().toLocalDate());

            notiResDTO = notiEventResDTO;
        }
        // 이미지 파일 정보 매핑
        notification.getNotificationImageSet().forEach(image -> {
            notiResDTO.addImage(image.getUuid(), image.getFileName(), image.getImageOrder());
        });
        log.error("entityToNotiResDTO(), notiResDTO : " + notiResDTO);
        return notiResDTO;
    }

    public void addFileNames(NotificationRegDTO notificationRegDTO, Notification notification){

        notificationRegDTO.getFileNames().forEach(fileName ->{
            String[] arr = fileName.split("_");
            notification.addImage(arr[0], arr[1]);
        });
    }
    private static EventNotification dtoToEventEntity(NotificationRegDTO dto) {

        return EventNotification.builder()
                .name(dto.getName())
                .title(dto.getTitle())
                .message(dto.getMessage())
                .regDate(LocalDateTime.now())
                .isUse(dto.getIsUse())
                .isPopup(dto.getIsPopup())
                .eventStartTime(Util.convertStringToLocalDateTime(dto.getEventStartTime()))
                .eventEndTime(Util.convertStringToLocalDateTime(dto.getEventEndTime()))
                .build();
    }
    private static NewsNotification dtoToNewsEntity(NotificationRegDTO dto) {

        return NewsNotification.builder()
                .name(dto.getName())
                .title(dto.getTitle())
                .message(dto.getMessage())
                .regDate(LocalDateTime.now())
                .isUse(dto.getIsUse())
                .isPopup(dto.getIsPopup())
                .target("targetTemp")
                .build();
    }
}
