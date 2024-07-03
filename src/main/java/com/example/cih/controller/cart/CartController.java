package com.example.cih.controller.cart;


import com.example.cih.dto.PageRequestDTO;
import com.example.cih.dto.cart.CartDetailResDTO;
import com.example.cih.service.cart.CartService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
@Log4j2
//@PreAuthorize("hasRole('USER')")
public class CartController {
    private final CartService cartService;

    @ApiOperation(value = "장바구니 조회", notes = "장바구니에 있는 모든 상품을 조회")
    @GetMapping("/list")
    public String getCart(@ModelAttribute("pageRequestDto") PageRequestDTO pageRequestDTO,
                       Model model, Principal principal){

        List<CartDetailResDTO> listDto = cartService.getCartAll(principal.getName());

        listDto.forEach(log::error);
        model.addAttribute("responseDTO", listDto);

        return "/cart/cartList";
    }

}
