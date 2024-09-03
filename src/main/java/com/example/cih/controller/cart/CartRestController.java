package com.example.cih.controller.cart;


import com.example.cih.domain.cart.Cart;
import com.example.cih.domain.user.User;
import com.example.cih.dto.shop.ItemBuyReqDTO;
import com.example.cih.service.cart.CartService;
import com.example.cih.service.user.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
@Log4j2
//@PreAuthorize("hasRole('USER')")
public class CartRestController {
    private final CartService cartService;
    private final UserService userService;

    @ApiOperation(value = "장바구니 상품 넣기", notes = "아이템 add 처리")
    @PostMapping("/add")
    public Map<String,String> postAdd(@Valid @RequestBody ItemBuyReqDTO itemBuyReqDTO,
                          Principal principal){

        User user = userService.findUser(principal.getName());

        cartService.addCart(itemBuyReqDTO, user);

        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("result", "success");

        return resultMap;
    }

    @ApiOperation(value = "장바구니 상품 취소", notes = "DELETE 방식으로 특정 상품 삭제")
    @DeleteMapping("/{cartId}")
    public Map<String, String> postRemove(@PathVariable("cartId") Long cartId ){

        Cart cart = cartService.deleteInCart(cartId);

        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("result", "success");

        return resultMap;
    }

}
