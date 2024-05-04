package com.example.cih.admin.shop;


import com.example.cih.domain.shop.ShopItem;
import com.example.cih.dto.PageRequestDTO;
import com.example.cih.dto.car.CarViewDTO;
import com.example.cih.dto.order.OrderDTO;
import com.example.cih.dto.shop.ShopItemDTO;
import com.example.cih.dto.shop.ShopItemViewDTO;
import com.example.cih.dto.user.UserDTO;
import com.example.cih.service.shop.ShopItemService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
@Log4j2
public class AdminShopController {

    private final ShopItemService shopItemService;

    @GetMapping("/shopItem")
    public String getShopItem(Model model){

        List<ShopItemViewDTO> allItems = shopItemService.getAllItems();

        allItems.forEach(log::error);

        model.addAttribute("itemList", allItems);

        return "/admin/shopItem";
    }

    @ApiOperation(value = "상품 데이터 넣기", notes = "테스트 용")
    @PostMapping("/shopItem")
    public String postShopItem(ShopItemDTO shopItemDTO,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes){

        if(bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(log::error);

            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/admin/shopItem";
        }

        Long ItemId = shopItemService.registerItem(shopItemDTO);

        return "redirect:/admin/shopItem";
    }

    @GetMapping("/shopItem/{shopItemId}")
    public String shopItemDetail(PageRequestDTO pageRequestDTO,
                                    @PathVariable("shopItemId") Long shopItemId,
                                    Model model){

        ShopItemViewDTO shopItem = shopItemService.findOne(shopItemId);

        model.addAttribute("responseDTO", shopItem);

        return "/admin/shopItemDetail";
    }

}
