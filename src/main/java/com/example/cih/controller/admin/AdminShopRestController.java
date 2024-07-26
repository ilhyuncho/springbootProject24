package com.example.cih.controller.admin;


import com.example.cih.common.util.Util;
import com.example.cih.dto.ImageOrderReqDTO;
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
    private final AdmnShopControllerValidator admnShopControllerValidator;

    @ApiOperation(value = "상품 데이터 넣기", notes = "관리자용")
    @PostMapping("/registerShopItem")
    public ResponseEntity<Map<String, String>> postRegisterShopItem(@Valid @RequestBody ShopItemReqDTO shopItemReqDTO
            , BindingResult bindingResult) throws BindException {

        if(bindingResult.hasErrors()) {
            log.error("bindingResult.hasErrors()~~~");

//            Map<String, String> resultMap = new HashMap<>();
//            resultMap.put("result", "fail");
//            resultMap.put("message", "전달 값 오류!!!");
//            return ResponseEntity.badRequest().body(resultMap);

            throw new BindException(bindingResult);
        }

        //admnShopControllerValidator.validate(shopItemReqDTO, bindingResult);
        if(shopItemReqDTO.getItemName().isEmpty()){
            shopItemReqDTO.setItemName(Util.createRandomName("Item"));
        }

        Long ItemId = shopItemService.registerItem(shopItemReqDTO);

        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("result", "success");
        resultMap.put("ItemId", ItemId.toString());

        return ResponseEntity.status(HttpStatus.OK).body(resultMap);
    }

    @PostMapping("/modifyShopItem")
    public ResponseEntity<Map<String, String>> postShopItemModify(@Valid @RequestBody ShopItemReqDTO shopItemReqDTO,
                                      BindingResult bindingResult) throws BindException {

        if(bindingResult.hasErrors()) {
            log.error("bindingResult.hasErrors()~~~");
            throw new BindException(bindingResult);
        }

        shopItemService.modifyItem(shopItemReqDTO);

        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("result", "success");
      //  resultMap.put("ItemId", ItemId.toString());

        return ResponseEntity.status(HttpStatus.OK).body(resultMap);
    }
    @PostMapping("/modifyImageOrder")
    public ResponseEntity<Map<String, String>> postImageOrderModify(@Valid @RequestBody ImageOrderReqDTO imageOrderReqDTO,
                                                                  BindingResult bindingResult) throws BindException {

        // ImageOrder 값이 중복 되는지 체크
        admnShopControllerValidator.validate(imageOrderReqDTO, bindingResult);

        if(bindingResult.hasErrors()) {
            log.error("bindingResult.hasErrors()~~~");
            throw new BindException(bindingResult);
        }

        shopItemService.modifyImageOrder(imageOrderReqDTO);

        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("result", "success");
        return ResponseEntity.status(HttpStatus.OK).body(resultMap);
    }


}
