package com.example.cih.controller.shop;

import com.example.cih.dto.shop.ShopItemSimpleDTO;
import com.example.cih.dto.shop.ShopItemViewDTO;
import com.example.cih.service.shop.ShopItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequestMapping("/shop")
@RequiredArgsConstructor
@Log4j2
public class ShopController {

    private final ShopItemService shopItemService;

    @GetMapping("/main")
    public String shopMain(Model model){

        List<ShopItemSimpleDTO> allItems = shopItemService.getAllItemsForShop();

        //allItems.forEach(log::error);
        model.addAttribute("itemList", allItems);

        return "/shop/mainNew";
    }

    @GetMapping("/itemDetail/{shopItemId}")
    public String shopItemDetailOrModify(@PathVariable("shopItemId") Long shopItemId,
                                         Model model){

        ShopItemViewDTO shopItem = shopItemService.findItem(shopItemId);

        model.addAttribute("responseDTO", shopItem);

        return "/shop/itemDetail";
    }

}
