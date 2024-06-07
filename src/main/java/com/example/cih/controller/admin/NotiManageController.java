package com.example.cih.controller.admin;

import com.example.cih.dto.PageRequestDTO;
import com.example.cih.dto.PageResponseDTO;
import com.example.cih.dto.notification.*;
import com.example.cih.service.notification.NotificationService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
@Log4j2
public class NotiManageController {
    private final NotificationService notificationService;

    @ApiOperation(value = "[이벤트] 관리 페이지 접근", notes = "관리자 접근")
    @GetMapping("/eventList")
    public String getEventRegister(@ModelAttribute("pageRequestDto") PageRequestDTO pageRequestDTO
                                   ,Model model) {

        PageResponseDTO<NotiEventResDTO> listDto = notificationService.getListEventInfo(pageRequestDTO);

        model.addAttribute("responseDTO", listDto);

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

    @ApiOperation(value = "[이벤트] 수정 페이지 접근", notes = "관리자 접근")
    @GetMapping("/eventModify/{notiId}")
    public String getEventModify(@PathVariable("notiId") Long notiId,
                                 Model model) {

        NotiEventResDTO eventInfo = notificationService.getEventInfo(notiId);

        model.addAttribute("responseDTO", eventInfo);

        return "/admin/eventModify";
    }

    @ApiOperation(value = "이벤트 세부 정보 변경 (post)", notes = "")
    @PostMapping("/eventModify/{notiId}")
    public String postEventModify(@PathVariable("notiId") Long notiId,
                                  NotificationRegDTO notificationRegDTO,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes,
                                Principal principal ){
        log.error("eventModify post....notiId : " + notiId);
        log.error("eventModify post....NotificationRegDTO : " + notificationRegDTO);

        if(bindingResult.hasErrors()){
            log.error("has errors.....");

            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            //redirectAttributes.addAttribute("carId", carInfoDTO.getCarId());
           // return "redirect:/myPage/carDetail?" + link;
        }

        notificationService.modifyEventNotification(notiId, notificationRegDTO );

        return "redirect:/admin/eventDetail/" + notiId;
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
    public String getNewsRegister(@ModelAttribute("pageRequestDto") PageRequestDTO pageRequestDTO
                                  ,Model model) {

        PageResponseDTO<NotiNewsResDTO> listDto = notificationService.getListNewsInfo(pageRequestDTO);

        model.addAttribute("responseDTO", listDto);

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