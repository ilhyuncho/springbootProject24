package com.example.cih.controller.cart;


import com.example.cih.domain.cart.Cart;
import com.example.cih.dto.cart.CartReqDTO;
import com.example.cih.service.cart.CartService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/cartRest")
@RequiredArgsConstructor
@Log4j2
//@PreAuthorize("hasRole('USER')")
public class CartRestController {
    private final CartService cartService;

    @ApiOperation(value = "장바구니 상품 취소", notes = "DELETE 방식으로 특정 상품 삭제")
    @DeleteMapping("/{cartId}")
    public Map<String,Long> postRemove(@PathVariable("cartId") Long cartId ){

        Cart cart = cartService.deleteInCart(cartId);

        Map<String, Long> resultMap = new HashMap<>();
        resultMap.put("cartId", cartId);

        return resultMap;
    }
    @ApiOperation(value="장바구니 아이템 수량 변경", notes = "PUT 방식으로")
    @PutMapping(value="/{cartId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Long> postModify( @PathVariable("cartId") Long cartId, @RequestBody CartReqDTO cartReqDTO){

        cartReqDTO.setCartId(cartId);
        cartService.modify(cartReqDTO);

        Map<String, Long> resultMap = new HashMap<>();
        resultMap.put("cartId", cartId);

        return resultMap;
    }

}
