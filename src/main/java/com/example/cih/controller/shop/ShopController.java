package com.example.cih.controller.shop;

import com.example.cih.domain.user.User;
import com.example.cih.dto.shop.ShopItemExtandDTO;
import com.example.cih.dto.shop.ShopItemResDTO;
import com.example.cih.service.shop.ShopItemService;
import com.example.cih.service.user.UserService;
import io.swagger.annotations.ApiOperation;
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

    private final UserService userService;
    private final ShopItemService shopItemService;

    @ApiOperation(value = "악세서리 샵 메인", notes = "회원, 비회원 접근 가능")
    @GetMapping("/main")
    public String shopMain(Model model){

        // 프로토타입 스코프 빈 테스트용 ( shopItemService 가 각각 생성되어 전달 됨
        log.error(shopItemService);

        List<ShopItemResDTO> listDTO = shopItemService.getAllItemsForShop();
        model.addAttribute("itemList", listDTO);

        for (ShopItemResDTO shopItemResDTO : listDTO) {
            log.error(shopItemResDTO);
        }
        return "/shop/main";
    }

    @ApiOperation(value = "악세서리 샵 아이템 선택시", notes = "회원, 비회원 접근 가능")
    @GetMapping("/itemDetail/{shopItemId}")
    public String shopItemDetail(@PathVariable("shopItemId") Long shopItemId, Model model,
             Principal principal){

        User user = null;
        if( principal != null){
            user = userService.findUser(principal.getName());
        }

        ShopItemExtandDTO itemDTO = shopItemService.getItemInfo(shopItemId, user);    // 악세서리 아이템 상세
        model.addAttribute("responseDTO", itemDTO);

        return "/shop/itemDetail";
    }

}
