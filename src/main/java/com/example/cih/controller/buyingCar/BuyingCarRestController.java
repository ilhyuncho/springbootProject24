package com.example.cih.controller.buyingCar;

import com.example.cih.domain.buyingCar.BuyingCar;
import com.example.cih.domain.buyingCar.BuyingCarRepository;
import com.example.cih.domain.sellingCar.SellingCar;
import com.example.cih.domain.sellingCar.SellingCarRepository;
import com.example.cih.domain.user.User;
import com.example.cih.dto.PageRequestDTO;
import com.example.cih.dto.PageResponseDTO;
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
    private final BuyingCarRepository buyingCarRepository;

    @ApiOperation(value = "차량 구매 제안(&가격 수정)", notes = "희망 가격 전달")
    @PostMapping("/offer")
    public Map<String,String> postOffer(@Valid @RequestBody BuyingCarRegDTO buyingCarRegDTO,
                                                 BindingResult bindingResult,
                                                 Principal principal ) throws BindException {
        log.error("buyingCar offer post...." + buyingCarRegDTO);

        if(bindingResult.hasErrors()){
            log.error("has errors.....");
            throw new BindException(bindingResult);
        }

        User user = userService.findUser(principal.getName());

        if(buyingCarRegDTO.getOfferType().equals("new")){
            buyingCarService.registerBuyingCar(user, buyingCarRegDTO);
        }
        else{
            buyingCarService.modifyBuyingCar(user, buyingCarRegDTO);
        }

        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("result", "success");

        return resultMap;
    }

    @ApiOperation(value = "차량 구매 취소", notes = "데이터 삭제 요청")
    @PostMapping("/cancel")
    public Map<String,String> postCancel(@Valid @RequestBody BuyingCarRegDTO buyingCarRegDTO,
                                    BindingResult bindingResult,
                                    Principal principal ) throws BindException {

        log.error("buyingCar cancel Delete...." + buyingCarRegDTO);

        if(bindingResult.hasErrors()){
            log.error("has errors.....");
            throw new BindException(bindingResult);
        }

        User user = userService.findUser(principal.getName());

        buyingCarService.deleteBuyingCar(user, buyingCarRegDTO);

        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("result", "success");

        return resultMap;
    }

    @ApiOperation(value = "구매 제안 리스트 전달", notes = "list 전달")
    @GetMapping("/list")
    public PageResponseDTO<BuyingCarViewDTO> getBuyingCarList(PageRequestDTO pageRequestDTO,
                                                          String userName,
                                                          Long sellingCarId){

        PageResponseDTO<BuyingCarViewDTO> pageBuyingCarViewDTO =
                buyingCarService.getListBuyingCarInfo(pageRequestDTO, sellingCarId);

        return pageBuyingCarViewDTO;
    }
    @ApiOperation(value = "구매 희망 최고 가격", notes = "")
    @GetMapping("/highProposalPrice")
    public BuyingCarViewDTO getHighProposalPrice(Long sellingCarId){

        BuyingCarViewDTO highProposalBuyingCar = buyingCarService.getHighProposalBuyingCar(sellingCarId);

        return highProposalBuyingCar;
    }


}
