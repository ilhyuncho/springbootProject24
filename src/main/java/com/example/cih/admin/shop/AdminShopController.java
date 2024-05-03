package com.example.cih.admin.shop;


import com.example.cih.dto.order.OrderDTO;
import com.example.cih.dto.shop.ShopItemDTO;
import com.example.cih.service.shop.ShopItemService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
@Log4j2
public class AdminShopController {

    private final ShopItemService shopItemService;

    @GetMapping("/shopItem")
    public String shopMain(){

        return "/admin/shopItem";
    }

    @ApiOperation(value = "상품 데이터 넣기", notes = "테스트 용")
    @PostMapping("/shopItem")
    public String Sample(ShopItemDTO shopItemDTO){

        Long order = shopItemService.registerItem(shopItemDTO);

        return "/admin/shopItem";
    }

}
