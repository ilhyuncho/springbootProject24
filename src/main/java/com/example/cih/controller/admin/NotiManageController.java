package com.example.cih.controller.admin;

import com.example.cih.dto.PageRequestDTO;
import com.example.cih.dto.notification.*;
import com.example.cih.service.notification.NotificationService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
@Log4j2
public class NotiManageController {
    private final NotificationService notificationService;

    @ApiOperation(value = "[이벤트] 관리 페이지 접근", notes = "관리자 접근")
    @GetMapping("/eventList")
    public String getEventRegister(Model model) {

        PageRequestDTO pageRequestDTO = new PageRequestDTO();

        List<NotiEventResDTO> listDto = notificationService.getListEventInfo(pageRequestDTO);

        model.addAttribute("listDto", listDto);

        return "/admin/eventRegister";
    }

    @ApiOperation(value = "[이벤트] 상세 페이지 접근", notes = "관리자 접근")
    @GetMapping("/eventDetail/{notiId}")
    public String getEventDetail(@PathVariable("notiId") Long notiId,
                                 Model model) {

        NotiEventResDTO eventInfo = notificationService.getEventInfo(notiId);

        model.addAttribute("responseDTO", eventInfo);

        return "/admin/eventDetail";
    }

    @ApiOperation(value = "[이벤트] 신규 등록", notes = "관리자 접근")
    @PostMapping("/eventRegister")
    public String postEventRegister(NotificationRegDTO notificationRegDTO,
                                    BindingResult bindingResult,
                                    RedirectAttributes redirectAttributes){

        if(bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(log::error);

            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/admin/eventList";
        }

        Long NotiId = notificationService.registerEventNotification(notificationRegDTO);

        return "redirect:/admin/eventList";
    }

    @ApiOperation(value = "[뉴스] 관리 페이지 접근", notes = "관리자 접근")
    @GetMapping("/newsList")
    public String getNewsRegister(Model model) {

        PageRequestDTO pageRequestDTO = new PageRequestDTO();

        List<NotiNewsResDTO> listDto = notificationService.getListNewsInfo(pageRequestDTO);

        model.addAttribute("listDto", listDto);

        return "/admin/newsRegister";
    }

    @ApiOperation(value = "[뉴스] 신규 등록", notes = "관리자 접근")
    @PostMapping("/newsRegister")
    public String postNewsRegister(NotificationRegDTO notificationRegDTO,
                                   BindingResult bindingResult,
                                   RedirectAttributes redirectAttributes){

        if(bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(log::error);

            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/admin/newsList";
        }

        Long NotiId = notificationService.registerNewsNotification(notificationRegDTO);

        return "redirect:/admin/newsList";
    }

    @ApiOperation(value = "[뉴스] 상세 페이지 접근", notes = "관리자 접근")
    @GetMapping("/newsDetail/{notiId}")
    public String getNewsDetail(@PathVariable("notiId") Long notiId,
                                Model model) {

        NotiNewsResDTO newsInfo = notificationService.getNewsInfo(notiId);

        model.addAttribute("responseDTO", newsInfo);
        //model.addAttribute("targetId", "news");

        return "/admin/newsDetail";
    }
}