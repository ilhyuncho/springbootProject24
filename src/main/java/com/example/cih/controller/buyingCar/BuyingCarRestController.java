package com.example.cih.controller.buyingCar;

import com.example.cih.domain.user.User;
import com.example.cih.dto.buyingCar.BuyingCarListResDTO;
import com.example.cih.dto.PageRequestDTO;
import com.example.cih.dto.buyingCar.BuyingCarRegDTO;
import com.example.cih.dto.buyingCar.BuyingCarViewDTO;
import com.example.cih.service.buyingCar.BuyingCarService;
import com.example.cih.service.user.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.*;


@RestController
@RequestMapping("/buyingCar")
@RequiredArgsConstructor
@Log4j2
public class BuyingCarRestController {

    private final BuyingCarService buyingCarService;
    private final UserService userService;

    @ApiOperation(value = "차량 구매 제안 or 차량 구매 상담 요청", notes = "")
    @PostMapping("/offer")
    public Map<String,String> postOffer(@Valid @RequestBody BuyingCarRegDTO buyingCarRegDTO,
                                                 BindingResult bindingResult,
                                                 Principal principal ) throws BindException {
        if(bindingResult.hasErrors()){
            log.error("has errors.....");
            throw new BindException(bindingResult);
        }

        User user = userService.findUser(principal.getName());

        buyingCarService.registerBuyingCar(user, buyingCarRegDTO);

        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("result", "success");
        return resultMap;
    }

    @ApiOperation(value = "차량 구매( or 상담 요청 ) 취소", notes = "")
    @PostMapping("/update")
    public Map<String,String> postCancel(@Valid @RequestBody BuyingCarRegDTO buyingCarRegDTO,
                                    BindingResult bindingResult,
                                    Principal principal ) throws BindException {

        if(bindingResult.hasErrors()){
            log.error("has errors.....");
            throw new BindException(bindingResult);
        }

        User user = userService.findUser(principal.getName());

        buyingCarService.updateBuyingCar(user, buyingCarRegDTO);

        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("result", "success");
        return resultMap;
    }

    @ApiOperation(value = "구매 제안 리스트 전달", notes = "list 전달")
    @GetMapping("/list")
    public BuyingCarListResDTO<BuyingCarViewDTO> getBuyingCarList(PageRequestDTO pageRequestDTO,
                                                          Long sellingCarId){

        return buyingCarService.getListBuyingCarInfo(pageRequestDTO, sellingCarId);
    }
    @ApiOperation(value = "구매 희망 최고 가격", notes = "")
    @GetMapping("/highProposalPrice")
    public BuyingCarViewDTO getHighProposalPrice(Long sellingCarId){

        return buyingCarService.getHighProposalBuyingCar(sellingCarId);
    }

}
