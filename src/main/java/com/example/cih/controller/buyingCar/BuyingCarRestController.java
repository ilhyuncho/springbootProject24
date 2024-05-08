package com.example.cih.controller.buyingCar;

import com.example.cih.dto.PageRequestDTO;
import com.example.cih.dto.buyingCar.BuyingCarRegDTO;
import com.example.cih.dto.buyingCar.BuyingCarViewDTO;
import com.example.cih.service.buyingCar.BuyingCarService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/buyingCar")
@RequiredArgsConstructor
@Log4j2
public class BuyingCarRestController {

    private final BuyingCarService buyingCarService;

    @ApiOperation(value = "판매 차량 구매 제안", notes = "희망 가격 전달")
    @PostMapping("/offer")
    public Map<String,String> offer(@Valid @RequestBody BuyingCarRegDTO buyingCarRegDTO,
                                                 BindingResult bindingResult,
                                                 Principal principal ) throws BindException {
        log.error("buyingCar offer post...." + buyingCarRegDTO);

        if(bindingResult.hasErrors()){
            log.error("has errors.....");
            throw new BindException(bindingResult);
        }

        buyingCarService.registerBuyingCar(principal.getName(), buyingCarRegDTO);

        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("result", "success");

        return resultMap;
    }

    @ApiOperation(value = "구매 제안 리스트 전달", notes = "")
    @GetMapping("/list")
    public List<BuyingCarViewDTO> listBuyingCar(PageRequestDTO pageRequestDTO,
                                                 Long sellingCarId){

        log.error("sellingCarId:"+sellingCarId);

        List<BuyingCarViewDTO> listBuyingCar = buyingCarService.getListBuyingCar(sellingCarId);

        return listBuyingCar;
    }
}
