package com.example.cih.controller.admin;


import com.example.cih.common.util.Util;
import com.example.cih.dto.shop.ShopItemReqDTO;
import com.example.cih.service.shop.ShopItemService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@Log4j2
public class AdminShopRestController {

    private final ShopItemService shopItemService;

    @ApiOperation(value = "상품 데이터 넣기", notes = "관리자용")
    @PostMapping("/shopItem")
    public ResponseEntity<Map<String, String>> postShopItem(@Valid @RequestBody ShopItemReqDTO shopItemReqDTO
            , BindingResult bindingResult) throws BindException {

        if(bindingResult.hasErrors()) {
            log.error("bindingResult.hasErrors()~~~");
            throw new BindException(bindingResult);
        }

        // 테스트용
        if(shopItemReqDTO.getItemName().isEmpty()){
            shopItemReqDTO.setItemName(Util.createRandomName("Item"));
        }

        Long ItemId = shopItemService.registerItem(shopItemReqDTO);

        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("result", "success");
        resultMap.put("ItemId", ItemId.toString());

        return ResponseEntity.status(HttpStatus.OK).body(resultMap);
    }

}
