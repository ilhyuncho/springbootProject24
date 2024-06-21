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
                            .eventStartDate(noti.getEventStartDate())
                            .eventEndDate(noti.getEventEndDate())
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
    public NotiResDTO getNotiInfo(Long notiId){

        Optional<Notification> result = notificationRepository.findById(notiId);

        NotiResDTO notiNewsResDTO = entityToNotiResDTO(result.get());

        log.error("notiNewsResDTO : " + notiNewsResDTO);
        return notiNewsResDTO;
    }

    public Boolean isEventDuplication(EventType eventType, LocalDate regStartDate, LocalDate regEndDate){

        // 이벤트 기간 곂치는지 체크
        List<EventNotification> result = eventNotificationRepository.findByEventType(eventType);

        return result.stream().anyMatch(event -> {

            log.error("regStartDate : " + regStartDate + ",," + "regEndDate : " + regEndDate);
            log.error("getEventStartDate : " + event.getEventStartDate() + ",," + "getEventEndDate : " + event.getEventEndDate());
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
                .ifPresent(m -> {
                    throw new ItemNotFoundException("해당 이벤트 정보가 이미 존재 함");
                });

        LocalDate regStartDate = LocalDate.parse(eventDTO.getEventStartDate());
        LocalDate regEndDate = LocalDate.parse(eventDTO.getEventEndDate());
        EventType eventType = EventType.fromValue(eventDTO.getEventSelectType());

        // 이벤트 기간 곂치는지 체크
        if(isEventDuplication(eventType, regStartDate, regEndDate)){
            log.error("이벤트 기간 곂침~~~~~~~~~~~~~~~");
            return 0L;
        }

        EventNotification eventNotification = dtoToEventEntity(eventDTO);

        if(eventDTO.getFileNames() != null){
            addFileNames(eventDTO, eventNotification);
        }

        EventNotification saveItem = eventNotificationRepository.save(eventNotification);

        return saveItem.getNotiId();
    }

    @Override
    public Long registerNewsNotification(NotificationRegDTO newsDTO) {

        newsNotificationRepository.findByName(newsDTO.getName())
                .ifPresent(m -> {
                    throw new ItemNotFoundException("해당 뉴스 정보가 이미 존재 함");
                });

        NewsNotification newsNotification = dtoToNewsEntity(newsDTO);

        if(newsDTO.getFileNames() != null){
            addFileNames(newsDTO, newsNotification);
        }

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

        // 첨부파일 처리
        eventNotification.clearImages();

        if(dto.getFileNames() != null){
            for (String fileName : dto.getFileNames() ) {
                String[] index = fileName.split("_");
                eventNotification.addImage(index[0], index[1]);
            }
        }

        log.error("modifyEventNotification() eventNotification : " + eventNotification);
        EventNotification save = eventNotificationRepository.save(eventNotification);
    }

    @Override
    public void modifyNewsNotification(Long notiId, NotificationRegDTO dto) {

        NewsNotification newsNotification = newsNotificationRepository.findById(notiId)
                .orElseThrow(() -> new NoSuchElementException("해당 뉴스 정보가 존재하지않습니다"));

        newsNotification.changeInfo(dto.getName()
                , dto.getTitle()
                , dto.getMessage()
                , dto.getIsUse()
                , dto.getIsPopup());

       // newsNotification.changeTarget(dto.getTarget());

        // 첨부파일 처리
        newsNotification.clearImages();

        if(dto.getFileNames() != null){
            for (String fileName : dto.getFileNames() ) {
                String[] index = fileName.split("_");
                newsNotification.addImage(index[0], index[1]);
            }
        }

        log.error("modifyNewsNotification() newsNotification : " + newsNotification);
        NewsNotification save = newsNotificationRepository.save(newsNotification);
    }

    @Override
    public void deleteEventNotification(Long notiId) {
        EventNotification result = eventNotificationRepository.findById(notiId)
                .orElseThrow(() -> new NoSuchElementException("해당 이벤트 정보가 존재하지않습니다"));

        eventNotificationRepository.delete(result);
    }

    @Override
    public void deleteNewsNotification(Long notiId) {

        NewsNotification result = newsNotificationRepository.findById(notiId)
                .orElseThrow(() -> new NoSuchElementException("해당 뉴스 정보가 존재하지않습니다"));

        newsNotificationRepository.delete(result);
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

        EventNotification eventNotification = result.stream()
                .filter(event -> event.getEventStartDate().compareTo(now) <= 0  // 날짜 비교
                        && event.getEventEndDate().compareTo(now) >= 0)
                .findFirst().orElse(null);

        return eventNotification;
    }

    private NotiResDTO entityToNotiResDTO(Notification notification) {

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
                .eventStartDate(LocalDate.parse(dto.getEventStartDate()))
                .eventEndDate(LocalDate.parse(dto.getEventEndDate()))
                .eventType(EventType.fromValue(dto.getEventSelectType()))
                .eventValue(dto.getEventValue())
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
