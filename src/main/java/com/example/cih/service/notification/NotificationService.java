package com.example.cih.service.notification;

import com.example.cih.domain.notification.EventNotification;
import com.example.cih.domain.notification.EventType;
import com.example.cih.dto.ImageOrderReqDTO;
import com.example.cih.dto.PageRequestDTO;
import com.example.cih.dto.PageResponseDTO;
import com.example.cih.dto.notification.*;


public interface NotificationService {

    PageResponseDTO<NotiEventResDTO> getListEventInfo(PageRequestDTO pageRequestDTO);
    PageResponseDTO<NotiNewsResDTO> getListNewsInfo(PageRequestDTO pageRequestDTO);

    NotiEventResDTO getEventInfo(Long notiId);
    NotiNewsResDTO getNewsInfo(Long notiId);
    NotiResDTO getNotiInfo(Long notiId);

    Long registerEventNotification(NotificationRegDTO eventDTO);
    Long registerNewsNotification(NotificationRegDTO newsDTO);

    void modifyEventNotification(Long notiId, NotificationRegDTO notificationRegDTO);
    void modifyNewsNotification(Long notiId, NotificationRegDTO notificationRegDTO);

    void deleteEventNotification(Long notiId);
    void deleteNewsNotification(Long notiId);

    NotiEventResDTO getRandomPopupEventInfo();

    EventNotification getNowDoingEventInfo(EventType eventType);

    void modifyImageOrder(ImageOrderReqDTO imageOrderReqDTO);


}
