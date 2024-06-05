package com.example.cih.controller.notification;


import com.example.cih.dto.PageRequestDTO;
import com.example.cih.dto.notification.NotiEventResDTO;
import com.example.cih.dto.notification.NotiNewsResDTO;
import com.example.cih.dto.notification.NotiResDTO;
import com.example.cih.service.notification.NotificationService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/notification")
@RequiredArgsConstructor
@Log4j2
public class NotificationController {
    private final NotificationService notificationService;

    @ApiOperation(value = "[공지사항] 클릭 or 상세 화면에서 list클릭시", notes = "고객 접근")
    @GetMapping("/get/{targetId}")
    public String getNews(@PathVariable("targetId") String targetId, Model model){

        // html 파일에서 @PathVariable("targetId") 로 파악함
        //model.addAttribute("targetId", targetId);
        return "/notification/noti";
    }

    @ApiOperation(value = "뉴스&이벤트 상세 페이지로 이동", notes = "고객 접근")
    @GetMapping("/show/{pathName}/{notiId}")
    public String show(@PathVariable("notiId") Long notiId,
                       @PathVariable("pathName") String pathName,
                       Model model) {

        NotiResDTO eventInfo = notificationService.getNotiInfo(notiId); // 굳이 title, image외에는 필요 없어서 notificationRepository 를 통해 get

        model.addAttribute("responseDTO", eventInfo);
        model.addAttribute("targetId", pathName);

        return "/notification/notiDetail";
    }

}
