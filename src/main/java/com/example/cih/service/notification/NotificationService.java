package com.example.cih.service.notification;

import com.example.cih.dto.PageRequestDTO;
import com.example.cih.dto.notification.NotificationRegDTO;
import com.example.cih.dto.notification.NotificationResDTO;

import java.util.List;

public interface NotificationService {

    List<NotificationResDTO> getListEventInfo(PageRequestDTO pageRequestDTO);
    List<NotificationResDTO> getListNewsInfo(PageRequestDTO pageRequestDTO);

    NotificationResDTO getEventInfo(Long notiId);

    Long registerNotification(NotificationRegDTO notificationRegDTO);
    Long registerNewsNotification(NotificationRegDTO notificationRegDTO);


}
