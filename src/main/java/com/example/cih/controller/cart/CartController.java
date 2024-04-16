package com.example.cih.controller.cart;


import com.example.cih.dto.PageRequestDTO;
import com.example.cih.dto.PageResponseDTO;
import com.example.cih.dto.car.CarSpecDTO;
import com.example.cih.dto.cart.CartDTO;
import com.example.cih.service.cart.CartService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
@Log4j2
//@PreAuthorize("hasRole('USER')")
public class CartController {
    private final CartService cartService;

    @PostMapping("/add")
    public String add(@Valid CartDTO cartDTO,
                      BindingResult bindingResult,
                      RedirectAttributes redirectAttributes,
                      Principal principal){

        // 구현 하기
        cartService.addCart(cartDTO, principal.getName());
        
        return "/cart/add"; // 어디로 이동할지 정해야 함
    }

    @ApiOperation(value = "장바구니 조회", notes = "장바구니에 있는 모든 상품을 조회")
    @GetMapping("/list")
    public String list(@ModelAttribute("pageRequestDto") PageRequestDTO pageRequestDTO,
                       Model model,
                       Principal principal){

        PageResponseDTO<CartDTO> cartAll = cartService.getCartAll(pageRequestDTO, principal.getName());
        model.addAttribute("responseDTO", cartAll);

        return "/cart/cartList";
    }

    @ApiOperation(value = "선택상품 삭제", notes = "")
    @PostMapping("/cancel")
    public String cancel(Long cartId, Model model){

        log.error("Cart Cancel()~~~ ");
        
        cartService.deleteInCart(cartId);

        // 임시로
        return "redirect:/cart/list";
    }

}
