package com.example.cih.controller.cart;


import com.example.cih.dto.PageRequestDTO;
import com.example.cih.dto.PageResponseDTO;
import com.example.cih.dto.cart.CartDTO;
import com.example.cih.service.cart.CartService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
@Log4j2
//@PreAuthorize("hasRole('USER')")
public class CartController {
    private final CartService cartService;

    @PostMapping("/add")
    public String add(){

        // 구현 하기
        return "/cart/add";
    }

    @ApiOperation(value = "장바구니 조회", notes = "장바구니에 있는 모든 상품을 조회")
    @GetMapping("/list")
    public String list(@ModelAttribute("pageRequestDto") PageRequestDTO pageRequestDTO,
                       Model model,
                       Principal principal ){

        PageResponseDTO<CartDTO> cartAll = cartService.getCartAll(pageRequestDTO, principal.getName());
        model.addAttribute("responseDTO", cartAll);

        return "/cart/cartList";
    }

    @PostMapping("/cancel")
    public String cancel(Long orderId,
                              Model model) throws Exception {

        log.error("Cart Cancel()~~~ ");
        
//        cartService.orderCancel(orderId);

        // 임시로
        return "redirect:/myPage/userCarRegister";
    }

}
