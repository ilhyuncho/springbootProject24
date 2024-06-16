package com.example.cih.controller.shop;

import com.example.cih.service.shop.OrderService;
import com.example.cih.service.shop.ShopItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/shop")
@RequiredArgsConstructor
@Log4j2
//@PreAuthorize("hasRole('USER')")
public class ShopController {
//    private final OrderService orderService;
//    private final ShopItemService shopItemService;

    @GetMapping("/main")
    public void shopMain(){

    }
}
