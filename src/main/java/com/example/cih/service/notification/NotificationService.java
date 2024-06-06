package com.example.cih.service.notification;

import com.example.cih.dto.PageRequestDTO;
import com.example.cih.dto.PageResponseDTO;
import com.example.cih.dto.notification.*;


public interface NotificationService {

    PageResponseDTO<NotiEventResDTO> getListEventInfo(PageRequestDTO pageRequestDTO);
    PageResponseDTO<NotiNewsResDTO> getListNewsInfo(PageRequestDTO pageRequestDTO);

    NotiEventResDTO getEventInfo(Long notiId);
    NotiNewsResDTO getNewsInfo(Long notiId);

    NotiResDTO getNotiInfo(Long notiId);

    Long registerEventNotification(NotificationRegDTO notificationRegDTO);
    Long registerNewsNotification(NotificationRegDTO notificationRegDTO);

    NotiEventResDTO getRandomPopupEventInfo();


}
