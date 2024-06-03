package com.example.cih.service.notification;

import com.example.cih.dto.PageRequestDTO;
import com.example.cih.dto.notification.NotificationRegDTO;
import com.example.cih.dto.notification.NotificationResDTO;

import java.util.List;

public interface NotificationService {

    List<NotificationResDTO> readEventNotification(PageRequestDTO pageRequestDTO);
    List<NotificationResDTO> readNewsNotification(PageRequestDTO pageRequestDTO);

    Long registerNotification(NotificationRegDTO notificationRegDTO);


}
