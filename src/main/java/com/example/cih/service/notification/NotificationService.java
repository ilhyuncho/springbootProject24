package com.example.cih.service.notification;

import com.example.cih.dto.PageRequestDTO;
import com.example.cih.dto.notification.*;

import java.util.List;

public interface NotificationService {

    List<NotiEventResDTO> getListEventInfo(PageRequestDTO pageRequestDTO);
    List<NotiNewsResDTO> getListNewsInfo(PageRequestDTO pageRequestDTO);

    NotiEventResDTO getEventInfo(Long notiId);
    NotiNewsResDTO getNewsInfo(Long notiId);

    Long registerNotification(NotificationRegDTO notificationRegDTO);
    Long registerNewsNotification(NotificationRegDTO notificationRegDTO);


}
