package com.example.cih.controller.cart;


import com.example.cih.config.ConfigProperties;
import com.example.cih.domain.user.User;
import com.example.cih.dto.PageRequestDTO;
import com.example.cih.dto.cart.CartDetailResDTO;
import com.example.cih.dto.user.UserAddressBookResDTO;
import com.example.cih.service.cart.CartService;
import com.example.cih.service.user.UserAddressBookService;
import com.example.cih.service.user.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
@Log4j2
@PreAuthorize("hasRole('USER')")
public class CartController {
    private final CartService cartService;
    private final UserService userService;
    private final UserAddressBookService userAddressBookService;

    private final ConfigProperties configProperties;

    @ApiOperation(value = "장바구니 조회", notes = "장바구니에 있는 모든 상품을 조회")
    @GetMapping("/list")
    public String getCart(@ModelAttribute("pageRequestDto") PageRequestDTO pageRequestDTO,
                       Model model, Principal principal){

        // test
        log.error(configProperties.getHostName());
        log.error(configProperties.getSendName());
        log.error(configProperties.getPort());

        // 속성 변경이 가능
        configProperties.setPort(8090);


        User user = userService.findUser(principal.getName());

        List<CartDetailResDTO> listDto = cartService.getCartAll(user);

        UserAddressBookResDTO mainAddressInfo = userAddressBookService.getMainAddressInfo(user);

        model.addAttribute("responseDTO", listDto);
        model.addAttribute("mainAddressInfo", mainAddressInfo);
        model.addAttribute("mPoint", user.getMPoint());

        return "/cart/cartList";
    }

}
