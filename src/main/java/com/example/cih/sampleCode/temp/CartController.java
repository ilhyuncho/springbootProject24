package com.example.cih.sampleCode.temp;


import com.example.cih.dto.PageRequestDTO;
import com.example.cih.dto.PageResponseDTO;
import com.example.cih.service.car.CarService;
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

    @GetMapping("/list")
    public String list(@ModelAttribute("pageRequestDto") PageRequestDTO pageRequestDTO,
                       Model model,
                       Principal principal ){

        PageResponseDTO<CartDTO> cartAll = cartService.getCartAll(pageRequestDTO, principal.getName());

        model.addAttribute("responseDTO", cartAll);

        return "/cart/list";
    }
    @GetMapping("/orderDetail")
    public String get(Long orderId,
                      Model model){

        CartDetailDTO orderDetailDto = cartService.getOrderDetail(orderId);

        model.addAttribute("responseDTO", orderDetailDto);
        return "/cart/orderDetail";
    }

    @PostMapping("/orderCancel")
    public String orderCancel(Long orderId,
                              Model model) throws Exception {

        log.error("orderCancel()~~~ ");
        
        cartService.orderCancel(orderId);

        
        // 임시로
        return "redirect:/myPage/userCarRegister";
    }

}
