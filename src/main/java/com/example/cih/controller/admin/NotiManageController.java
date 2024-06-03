package com.example.cih.controller.admin;

import com.example.cih.common.handler.FileHandler;
import com.example.cih.dto.PageRequestDTO;
import com.example.cih.dto.notification.NotificationRegDTO;
import com.example.cih.dto.notification.NotificationResDTO;
import com.example.cih.dto.shop.ShopItemViewDTO;
import com.example.cih.service.notification.NotificationService;
import com.example.cih.service.shop.ShopItemService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/eventRegister")
    public String getEventRegister(Model model) {

        PageRequestDTO pageRequestDTO = new PageRequestDTO();

        List<NotificationResDTO> listDto = notificationService.readEventNotification(pageRequestDTO);

        model.addAttribute("listDto", listDto);

        return "/admin/eventRegister";
    }

    @ApiOperation(value = "이벤트넣기", notes = "")
    @PostMapping("/eventRegister")
    public String postEventRegister(NotificationRegDTO notificationRegDTO,
                                    BindingResult bindingResult,
                                    RedirectAttributes redirectAttributes){

        if(bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(log::error);

            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/admin/shopItem";
        }

        Long NotiId = notificationService.registerNotification(notificationRegDTO);

        return "redirect:/admin/eventRegister";
    }



}