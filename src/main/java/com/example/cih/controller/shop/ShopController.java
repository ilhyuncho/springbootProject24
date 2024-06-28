package com.example.cih.controller.shop;

import com.example.cih.domain.user.User;
import com.example.cih.dto.shop.ShopItemExtandDTO;
import com.example.cih.dto.shop.ShopItemDTO;
import com.example.cih.service.shop.ShopItemService;
import com.example.cih.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;


@Controller
@RequestMapping("/shop")
@RequiredArgsConstructor
@Log4j2
public class ShopController {

    private final ShopItemService shopItemService;
    private final UserService userService;

    @GetMapping("/main")
    public String shopMain(Model model){

        List<ShopItemDTO> listDTO = shopItemService.getAllItemsForShop();    // 악세서리 샵 메인 ( 심플 )

        model.addAttribute("itemList", listDTO);
        //allItems.forEach(log::error);
        return "/shop/main";
    }

    @GetMapping("/itemDetail/{shopItemId}")
    public String shopItemDetail(@PathVariable("shopItemId") Long shopItemId, Model model,
             Principal principal){

        User user = null;
        if( principal != null){
            user = userService.findUser(principal.getName());
        }

        ShopItemExtandDTO itemDTO = shopItemService.getItem(shopItemId, user);    // 악세서리 아이템 상세

        model.addAttribute("responseDTO", itemDTO);

        log.error(itemDTO);

        return "/shop/itemDetail";
    }

}
