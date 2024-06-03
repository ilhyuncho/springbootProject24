package com.example.cih.controller.notification;


import com.example.cih.dto.PageRequestDTO;
import com.example.cih.dto.notification.NotificationResDTO;
import com.example.cih.service.notification.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/notification")
@RequiredArgsConstructor
@Log4j2
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping("/news")
    public String getNews(PageRequestDTO pageRequestDTO, Model model){

        List<NotificationResDTO> listDTO = notificationService.getListNewsInfo(pageRequestDTO);

        model.addAttribute("list", listDTO);

        return "/notification/news";
    }
}
