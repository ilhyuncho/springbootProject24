package com.example.cih.controller.shop;


import com.example.cih.domain.shop.OrderDTO;
import com.example.cih.service.shop.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/shop")
@RequiredArgsConstructor
@Log4j2
//@PreAuthorize("hasRole('USER')")
public class ShopController {
    private final OrderService orderService;

    @GetMapping("/main")
    public void shopMain(){

    }

    @PostMapping("/order")
    public String Sample(OrderDTO orderDTO, String userName) throws Exception {

        Long order = orderService.order(userName, orderDTO.getItemID(), orderDTO.getCount());

        return "/shop/main";
    }
}
