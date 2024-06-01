package com.example.cih.controller.notification;


import com.example.cih.dto.PageRequestDTO;
import com.example.cih.dto.notification.NotificationResDTO;
import com.example.cih.dto.statistics.StatisticsResDTO;
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

    @GetMapping("/list")
    public List<NotificationResDTO> getList(PageRequestDTO pageRequestDTO,
                                            String targetId, Model model){


        List<NotificationResDTO> listDto = new ArrayList<>();
        if( "#event".equals(targetId)){
            listDto = notificationService.readEventNotification(pageRequestDTO);
        }
        else{
            listDto = notificationService.readNewsNotification(pageRequestDTO);
        }

        return listDto;
    }

}
