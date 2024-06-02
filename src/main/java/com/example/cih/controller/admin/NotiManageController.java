package com.example.cih.controller.admin;

import com.example.cih.common.handler.FileHandler;
import com.example.cih.dto.shop.ShopItemViewDTO;
import com.example.cih.service.shop.ShopItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
@Log4j2
public class NotiManageController {

    private final ShopItemService shopItemService;
    private final FileHandler fileHandler;

    @GetMapping("/eventRegister")
    public String getEventRegister(Model model) {

        List<ShopItemViewDTO> allItems = shopItemService.getAllItems();

        allItems.forEach(log::error);

        model.addAttribute("itemList", allItems);



        return "/admin/eventRegister";
    }

}