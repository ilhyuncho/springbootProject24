package com.example.cih.controller.shop;


import com.example.cih.domain.shop.ShopItemRepository;
import com.example.cih.dto.order.OrderDTO;
import com.example.cih.dto.shop.ShopItemDTO;
import com.example.cih.dto.shop.ShopItemViewDTO;
import com.example.cih.service.shop.OrderService;
import com.example.cih.service.shop.ShopItemService;
import com.example.cih.service.shop.ShopItemServiceImpl;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/shop")
@RequiredArgsConstructor
@Log4j2
//@PreAuthorize("hasRole('USER')")
public class ShopController {
    private final OrderService orderService;
    private final ShopItemService shopItemService;

    @GetMapping("/main")
    public void shopMain(){

    }
    @GetMapping("/items")
    public String items(Model model){

        List<ShopItemViewDTO> itemList = shopItemService.getAllItems();

        itemList.forEach(log::error);


        model.addAttribute("itemList", itemList);

        return "/shop/main";
    }
    @ApiOperation(value = "order 데이터 넣기", notes = "테스트 용")
    @PostMapping("/order")
    public String Sample(OrderDTO orderDTO, String userName){

        Long order = orderService.order(userName, orderDTO.getShopItemId(), orderDTO.getOrderCount());

        return "/shop/main";
    }

}
