package com.example.cih.controller.notification;


import com.example.cih.dto.PageRequestDTO;
import com.example.cih.dto.notification.NotiEventResDTO;
import com.example.cih.dto.notification.NotiNewsResDTO;
import com.example.cih.dto.notification.NotiResDTO;
import com.example.cih.service.notification.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/notification")
@RequiredArgsConstructor
@Log4j2
public class NotificationRestController {
    private final NotificationService notificationService;

    @GetMapping("/event")
    public List<NotiEventResDTO> getEventList(PageRequestDTO pageRequestDTO,
                                         String targetId, Model model){

        List<NotiEventResDTO> listEventInfo = notificationService.getListEventInfo(pageRequestDTO);
        return listEventInfo;
    }

    @GetMapping("/news")
    public List<NotiNewsResDTO> getNewsList(PageRequestDTO pageRequestDTO,
                                         String targetId, Model model){

        List<NotiNewsResDTO> listNewsInfo = notificationService.getListNewsInfo(pageRequestDTO);
        return listNewsInfo;
    }



}
