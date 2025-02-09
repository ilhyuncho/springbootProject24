package com.example.cih.controller.admin;

import com.example.cih.common.handler.FileHandler;
import com.example.cih.controller.admin.validator.NotiControllerValidator;
import com.example.cih.domain.notification.EventType;
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

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
@Log4j2
public class AdminNotiController {
    private final NotificationService notificationService;
    private final FileHandler fileHandler;
    private final NotiControllerValidator notiControllerValidator;

    @ApiOperation(value = "[이벤트] 관리 페이지 접근", notes = "관리자 접근")
    @GetMapping("/eventRegister")
    public String getEventRegister(@ModelAttribute("pageRequestDto") PageRequestDTO pageRequestDTO
                                   ,Model model) {

        PageResponseDTO<NotiEventResDTO> listDto = notificationService.getListEventInfo(pageRequestDTO);

        model.addAttribute("responseDTO", listDto);
        model.addAttribute("eventTypeList", EventType.getAllTypes());

        return "/admin/eventRegister";
    }
    @ApiOperation(value = "[이벤트] 상세, 수정 페이지 접근", notes = "관리자 접근")
    @GetMapping({"/eventDetail/{notiId}", "/eventModify/{notiId}"})
    public String getEventDetailOrModify(HttpServletRequest request,
                                         @PathVariable("notiId") Long notiId,
                                         Model model){

        NotiEventResDTO eventInfo = notificationService.getEventInfo(notiId);

        model.addAttribute("responseDTO", eventInfo);
        model.addAttribute("eventTypeList", EventType.getAllTypes());

        String requestURI = request.getRequestURI();
        return requestURI.substring(0, requestURI.lastIndexOf("/"));
    }

    @ApiOperation(value = "[이벤트] 신규 등록", notes = "관리자 접근")
    @PostMapping("/eventRegister")
    public String postEventRegister(NotificationRegDTO notificationRegDTO,
                                    BindingResult bindingResult,
                                    RedirectAttributes redirectAttributes){

        if(bindingResult.hasErrors()){
            bindingResult.getAllErrors().forEach(log::error);

            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/admin/eventRegister";
        }

        notiControllerValidator.validate(notificationRegDTO, bindingResult);

        Long eventId = notificationService.registerEventNotification(notificationRegDTO);
        if(eventId == 0){
            redirectAttributes.addFlashAttribute("errorMessage", "이벤트 기간 중복으로 등록 실패");
        }

        return "redirect:/admin/eventRegister";
    }

    @ApiOperation(value = "[이벤트] 세부 정보 변경 (post)", notes = "")
    @PostMapping("/eventModify/{notiId}")
    public String postEventModify(@PathVariable("notiId") Long notiId,
                                  NotificationRegDTO notificationRegDTO,
                                  BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes){

        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
        }

        notificationService.modifyEventNotification(notiId, notificationRegDTO );

        return "redirect:/admin/eventDetail/" + notiId;
    }

/////////////////////////////////////////////////////////////////////////////////////////
    @ApiOperation(value = "[뉴스] 관리 페이지 접근", notes = "관리자 접근")
    @GetMapping("/newsRegister")
    public String getNewsRegister(@ModelAttribute("pageRequestDto") PageRequestDTO pageRequestDTO,
                                  Model model) {

        PageResponseDTO<NotiNewsResDTO> listDto = notificationService.getListNewsInfo(pageRequestDTO);

        model.addAttribute("responseDTO", listDto);

        return "/admin/newsRegister";
    }

    @ApiOperation(value = "[뉴스] 상세, 수정 페이지 접근", notes = "관리자 접근")
    @GetMapping({"/newsDetail/{notiId}", "/newsModify/{notiId}"})
    public String getNewsDetailOrModify(HttpServletRequest request,
                                        @PathVariable("notiId") Long notiId,
                                        Model model) {

        NotiNewsResDTO newsInfo = notificationService.getNewsInfo(notiId);

        model.addAttribute("responseDTO", newsInfo);

        String requestURI = request.getRequestURI();
        return requestURI.substring(0, requestURI.lastIndexOf("/"));
    }
    @ApiOperation(value = "[뉴스] 신규 등록", notes = "관리자 접근")
    @PostMapping("/newsRegister")
    public String postNewsRegister(NotificationRegDTO notificationRegDTO,
                                   BindingResult bindingResult,
                                   RedirectAttributes redirectAttributes){

        if(bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(log::error);

            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/admin/newsRegister";
        }

        notiControllerValidator.validate(notificationRegDTO, bindingResult);

        Long newsId = notificationService.registerNewsNotification(notificationRegDTO);

        return "redirect:/admin/newsRegister";
    }
    @ApiOperation(value = "[뉴스] 세부 정보 변경 (post)", notes = "관리자 접근")
    @PostMapping("/newsModify/{notiId}")
    public String postNewsModify(@PathVariable("notiId") Long notiId,
                                  NotificationRegDTO notificationRegDTO,
                                  BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes){

        if(bindingResult.hasErrors()){
            log.error("has errors.....");
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/myPage/newsDetail/" + notiId;
        }

        notificationService.modifyNewsNotification(notiId, notificationRegDTO );

        return "redirect:/admin/newsDetail/" + notiId;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////

    @ApiOperation(value = "[뉴스] & [이벤트] 삭제", notes = "관리자 접근")
    @PostMapping("/notiDelete/{notiType}/{notiId}")
    public String postNotiDelete(@PathVariable("notiType") String notiType,
                                 @PathVariable("notiId") Long notiId,
                                 NotiDeleteRegDTO notiDeleteRegDTO,
                                 RedirectAttributes redirectAttributes){

        notificationService.deleteNotification(notiType, notiId);

        // 첨부파일 삭제
        List<String> fileNames = notiDeleteRegDTO.getFileNames();
        if(fileNames != null && fileNames.size() > 0){
            fileHandler.removeFiles(fileNames);
        }

        redirectAttributes.addFlashAttribute("result", "removed");

        return "redirect:/admin/" + notiType + "Register";
    }

    @ApiOperation(value = "[뉴스] & [이벤트] 이미지 순서 수정 페이지로 이동", notes = "관리자 접근")
    @GetMapping("/notiImageOrderModify/{notiType}/{notiId}")
    public String getNotiImageOrderModifyNew(@PathVariable("notiType") String notiType,
                                             @PathVariable("notiId") Long notiId, Model model){

        NotiResDTO notiInfo = notificationService.getNotiInfo(notiId);

        model.addAttribute("responseDTO", notiInfo);

        return "/admin/" + notiType + "ImageOrderModify";
    }

}