package com.example.cih.controller.notification;


import com.example.cih.dto.PageRequestDTO;
import com.example.cih.dto.PageResponseDTO;
import com.example.cih.dto.notification.NotiEventResDTO;
import com.example.cih.dto.notification.NotiNewsResDTO;
import com.example.cih.service.notification.NotificationService;
import io.swagger.annotations.ApiOperation;
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


    @ApiOperation(value = "[공지사항] 이벤트 리스트 전달", notes = "고객 접근")
    @GetMapping("/event")
    public PageResponseDTO<NotiEventResDTO> getEventList(PageRequestDTO pageRequestDTO){

        return notificationService.getListEventInfo(pageRequestDTO);
    }

    @ApiOperation(value = "[공지사항] 뉴스 리스트 전달", notes = "고객 접근")
    @GetMapping("/news")
    public PageResponseDTO<NotiNewsResDTO> getNewsList(PageRequestDTO pageRequestDTO){

        return notificationService.getListNewsInfo(pageRequestDTO);
    }



}
