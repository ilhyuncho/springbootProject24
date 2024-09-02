package com.example.cih.service.notification;

import com.example.cih.common.exception.ItemNotFoundException;
import com.example.cih.common.util.Util;
import com.example.cih.domain.notification.*;
import com.example.cih.dto.ImageDTO;
import com.example.cih.dto.ImageOrderReqDTO;
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
import java.util.Map;
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

    private final NotificationImageRepository notificationImageRepository;
    private final ModelMapper modelMapper;

    @Override
    public PageResponseDTO<NotiEventResDTO> getListEventInfo(PageRequestDTO pageRequestDTO) {

        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable("regDate");

        Page<EventNotification> result = eventNotificationRepository.searchAll(types, keyword, pageable);
        List<EventNotification> eventNotification = result.getContent();


        List<NotiEventResDTO> dtoList = eventNotification.stream()
                            .map(notification -> NotiEventResDTO.builder()
                                        .notiId(notification.getNotiId())
                                        .name(notification.getName())
                                        .title(notification.getTitle())
                                        .message(notification.getMessage())
                                        .regDate(Util.convertLocalDate(notification.getRegDate()))
                                        .eventStartDate(notification.getEventStartDate())
                                        .eventEndDate(notification.getEventEndDate())
                                        .isUse(notification.getIsUse())
                                        .isPopup(notification.getIsPopup())
                                        .build()
                            )
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

        Page<NewsNotification> result = newsNotificationRepository.searchAll(types, keyword, pageable);
        List<NotiNewsResDTO> dtoList = result.stream()
                            .map(notification -> NotiNewsResDTO.builder()
                                        .notiId(notification.getNotiId())
                                        .name(notification.getName())
                                        .title(notification.getTitle())
                                        .message(notification.getMessage())
                                        .regDate(Util.convertLocalDate(notification.getRegDate()))
                                        .target(notification.getNewsTarget())
                                        .isUse(notification.getIsUse())
                                        .isPopup(notification.getIsPopup())
                                        .build()
                            )
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

        EventNotification eventNotification = eventNotificationRepository.findById(notiId)
                .orElseThrow(() -> new NoSuchElementException("해당 이벤트 정보가 존재하지않습니다"));

        return (NotiEventResDTO)entityToNotiResDTO(eventNotification);
    }

    @Override
    public NotiNewsResDTO getNewsInfo(Long notiId) {

        NewsNotification newsNotification = newsNotificationRepository.findById(notiId)
            .orElseThrow(() -> new NoSuchElementException("해당 이벤트 정보가 존재하지않습니다"));

        return (NotiNewsResDTO)entityToNotiResDTO(newsNotification);
    }

    @Override
    public NotiResDTO getNotiInfo(Long notiId){ // 뉴스&이벤트 상세 페이지로 이동-고객이 접근

        Optional<Notification> result = notificationRepository.findById(notiId);

        return result.map(this::entityToNotiResDTO).orElse(null);
    }

    public Boolean isEventDuplication(EventType eventType, LocalDate regStartDate, LocalDate regEndDate){

        // 이벤트 기간 곂치는지 체크
        List<EventNotification> result = eventNotificationRepository.findByEventType(eventType);

        return result.stream().anyMatch(event -> {
                    // 이벤트 기간 체크
                    if (!regStartDate.isBefore(event.getEventStartDate()) && regStartDate.isBefore(event.getEventEndDate())) {
                        log.error("이벤트 시작 날짜 곂침~~~~~~~~~~~~~~~");
                        return true;
                    }
                    else if (!regEndDate.isBefore(event.getEventStartDate()) && (regEndDate.isBefore(event.getEventEndDate())
                            || regEndDate.equals(event.getEventEndDate()))) {
                        log.error("이벤트 종료 날짜 곂침~~~~~~~~~~~~~~~");
                        return true;
                    }
                    return false;
                });
    }

    @Override
    public Long registerEventNotification(NotificationRegDTO eventDTO) {

        eventNotificationRepository.findByName(eventDTO.getName())
                .ifPresent(m -> {throw new ItemNotFoundException("해당 이벤트 정보가 이미 존재 함");});

        LocalDate regStartDate = LocalDate.parse(eventDTO.getEventStartDate());
        LocalDate regEndDate = LocalDate.parse(eventDTO.getEventEndDate());
        EventType eventType = EventType.fromValue(eventDTO.getEventSelectType());

        // 이벤트 기간 곂치는지 체크
        if(isEventDuplication(eventType, regStartDate, regEndDate)){
            return 0L;
        }

        EventNotification eventNotification = dtoToEventEntity(eventDTO);

        EventNotification saveItem = eventNotificationRepository.save(eventNotification);

        return saveItem.getNotiId();
    }

    @Override
    public Long registerNewsNotification(NotificationRegDTO newsDTO) {

        newsNotificationRepository.findByName(newsDTO.getName())
                .ifPresent(m -> {throw new ItemNotFoundException("해당 뉴스 정보가 이미 존재 함");});

        NewsNotification newsNotification = dtoToNewsEntity(newsDTO);

        NewsNotification saveItem = newsNotificationRepository.save(newsNotification);

        return saveItem.getNotiId();
    }

    @Override
    public void modifyEventNotification(Long notiId, NotificationRegDTO dto) {

        EventNotification eventNotification = eventNotificationRepository.findById(notiId)
                .orElseThrow(() -> new NoSuchElementException("해당 이벤트 정보가 존재하지않습니다"));

        eventNotification.changeInfo(dto.getName()
                                    ,dto.getTitle()
                                    ,dto.getMessage()
                                    ,dto.getIsUse()
                                    ,dto.getIsPopup());

        eventNotification.changeEventInfo(LocalDate.parse(dto.getEventStartDate())
                                        ,LocalDate.parse(dto.getEventEndDate())
                                        ,EventType.fromValue(dto.getEventSelectType())
                                        ,dto.getEventValue());

        // 첨부 파일 갱신
        eventNotification.updateImages(dto);
    }

    @Override
    public void modifyNewsNotification(Long notiId, NotificationRegDTO dto) {

        NewsNotification newsNotification = newsNotificationRepository.findById(notiId)
                .orElseThrow(() -> new NoSuchElementException("해당 뉴스 정보가 존재하지않습니다"));

        newsNotification.changeInfo(dto.getName()
                                   ,dto.getTitle()
                                   ,dto.getMessage()
                                   ,dto.getIsUse()
                                   ,dto.getIsPopup());

       // newsNotification.changeTarget(dto.getTarget());

        // 첨부 파일 갱신
        newsNotification.updateImages(dto);
    }
    @Override
    public void deleteNotification(String notiType, Long notiId) {

        if(notiType.equals("event")){
            EventNotification result = eventNotificationRepository.findById(notiId)
                    .orElseThrow(() -> new NoSuchElementException("해당 이벤트 정보가 존재하지않습니다"));

            eventNotificationRepository.delete(result);
        }
        else if(notiType.equals("news")){
            NewsNotification result = newsNotificationRepository.findById(notiId)
                    .orElseThrow(() -> new NoSuchElementException("해당 뉴스 정보가 존재하지않습니다"));

            newsNotificationRepository.delete(result);
        }
    }

    @Override
    public NotiEventResDTO getRandomPopupEventInfo() {

        EventNotification eventNotification = eventNotificationRepository.searchTodayRandomEvent();

        if(eventNotification != null){

            // 에러 발생: The destination property com.example.cih.dto.notification.NotiResDTO.setRegDate()
            //  matches multiple source property hierarchies:

            // 해결책
            //modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
            // or
            modelMapper.getConfiguration().setAmbiguityIgnored(true);

            return modelMapper.map(eventNotification, NotiEventResDTO.class);
        }

        return null;
    }

    @Override
    public EventNotification getNowDoingEventInfo(EventType eventType) { // 현재 진행 중인 이벤트 정보 return

        LocalDate now = LocalDate.now();
        
        List<EventNotification> result = eventNotificationRepository.findByEventType(eventType);

        return result.stream()
                .filter(event -> event.getEventStartDate().compareTo(now) <= 0  // 날짜 비교
                        && event.getEventEndDate().compareTo(now) >= 0)
                .findFirst().orElse(null);
    }

    private NotiResDTO entityToNotiResDTO(Notification notification) {

        // 매핑을 할 때 변수명과 컬럼명이 일치하지 않을 수 있을 경우를 고려
        modelMapper.getConfiguration().setAmbiguityIgnored(true);

        NotiResDTO notiResDTO;
        if( notification.getClass().equals(NewsNotification.class)){
            NewsNotification noti = (NewsNotification)notification;

            NotiNewsResDTO notiNewsResDTO = modelMapper.map(noti, NotiNewsResDTO.class);

            notiResDTO = notiNewsResDTO;
        }
        else{
            EventNotification noti = (EventNotification)notification;

            NotiEventResDTO notiEventResDTO = modelMapper.map(noti, NotiEventResDTO.class);

            notiEventResDTO.setEventStartDate(notiEventResDTO.getEventStartDate());
            notiEventResDTO.setEventEndDate(notiEventResDTO.getEventEndDate());
            notiEventResDTO.setEventSelectType(noti.getEventType().getName());
            notiEventResDTO.setEventValue(noti.getEventValue());

            notiResDTO = notiEventResDTO;
        }
        // 이미지 파일 정보 매핑
        notification.getNotificationImageSet().forEach(image -> {
            notiResDTO.addImage(image.getNotificationImageId(), image.getUuid(), image.getFileName(),
                    image.getImageOrder(), image.getIsMainImage());
        });


        return notiResDTO;
    }

    public static void addFileNames(NotificationRegDTO notificationRegDTO, Notification notification){

        notificationRegDTO.getFileNames().forEach(fileName ->{
            String[] arr = fileName.split("_");
            notification.addImage(arr[0], arr[1]);
        });
    }
    private static EventNotification dtoToEventEntity(NotificationRegDTO dto) {

        EventNotification eventNotification = EventNotification.builder()
                .name(dto.getName())
                .title(dto.getTitle())
                .message(dto.getMessage())
                .regDate(LocalDateTime.now())
                .isUse(dto.getIsUse())
                .isPopup(dto.getIsPopup())
                .eventStartDate(LocalDate.parse(dto.getEventStartDate()))
                .eventEndDate(LocalDate.parse(dto.getEventEndDate()))
                .eventType(EventType.fromValue(dto.getEventSelectType()))
                .eventValue(dto.getEventValue())
                .build();

        if(dto.getFileNames() != null){
            addFileNames(dto, eventNotification);
        }

        return eventNotification;
    }
    private static NewsNotification dtoToNewsEntity(NotificationRegDTO dto) {

        NewsNotification newsNotification = NewsNotification.builder()
                .name(dto.getName())
                .title(dto.getTitle())
                .message(dto.getMessage())
                .regDate(LocalDateTime.now())
                .isUse(dto.getIsUse())
                .isPopup(dto.getIsPopup())
                .newsTarget("targetTemp")
                .build();

        if(dto.getFileNames() != null){
            addFileNames(dto, newsNotification);
        }
        
        return newsNotification;
    }

    @Override
    public void modifyImageOrder(ImageOrderReqDTO imageOrderReqDTO) {

        Optional<Notification> result = notificationRepository.findById(imageOrderReqDTO.getObjectId());

        if(result.isPresent()){
            // 관리자가 재설정한 image order
            Map<Long, Integer> mapImageDTO = imageOrderReqDTO.getImageOrderList().stream()
                    .collect(Collectors.toMap(ImageDTO::getImageId, ImageDTO::getImageOrder));

            // DB에 등록된 image order 데이터 get
            List<NotificationImage> listItemImage = notificationImageRepository.findByNotification(result.get());

            listItemImage.forEach(itemImage -> {
                itemImage.changeImageOrder(mapImageDTO.get(itemImage.getNotificationImageId()));
            });
        }

    }

}
