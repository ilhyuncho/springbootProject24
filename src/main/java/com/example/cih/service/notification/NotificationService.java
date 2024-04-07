package com.example.cih.service.notification;

import com.example.cih.dto.PageRequestDTO;
import com.example.cih.dto.notification.NotificationDTO;

import java.util.List;

public interface NotificationService {

    List<NotificationDTO> readNotification(PageRequestDTO pageRequestDTO);
}
