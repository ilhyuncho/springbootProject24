package com.example.cih.controller.buyingCar;

import com.example.cih.domain.buyingCar.BuyingCar;
import com.example.cih.domain.buyingCar.BuyingCarRepository;
import com.example.cih.domain.sellingCar.SellingCar;
import com.example.cih.domain.sellingCar.SellingCarRepository;
import com.example.cih.dto.PageRequestDTO;
import com.example.cih.dto.buyingCar.BuyingCarRegDTO;
import com.example.cih.dto.buyingCar.BuyingCarViewDTO;
import com.example.cih.dto.buyingCar.PageBuyingCarViewDTO;
import com.example.cih.service.buyingCar.BuyingCarService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    private final BuyingCarRepository buyingCarRepository;

    @ApiOperation(value = "차량 구매 제안", notes = "희망 가격 전달")
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

    @ApiOperation(value = "차량 구매 가격 수정", notes = "희망 가격 전달")
    @PostMapping("/modifyOffer")
    public Map<String,String> modifyOffer(@Valid @RequestBody BuyingCarRegDTO buyingCarRegDTO,
                                    BindingResult bindingResult,
                                    Principal principal ) throws BindException {
        log.error("buyingCar offer post...." + buyingCarRegDTO);

        if(bindingResult.hasErrors()){
            log.error("has errors.....");
            throw new BindException(bindingResult);
        }

        buyingCarService.modifyBuyingCar(principal.getName(), buyingCarRegDTO);

        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("result", "success");

        return resultMap;
    }

    @ApiOperation(value = "구매 제안 리스트 전달", notes = "")
    @GetMapping("/list")
    public PageBuyingCarViewDTO listBuyingCar(PageRequestDTO pageRequestDTO,
                                                 String userName,
                                                 Long sellingCarId){

        PageBuyingCarViewDTO pageBuyingCarViewDTO = new PageBuyingCarViewDTO();

        Pageable pageable = PageRequest.of(0,10);   // 임시

        Page<BuyingCarViewDTO> resultDTO = buyingCarRepository.getBuyingCarInfo(sellingCarId, pageable);
        List<BuyingCarViewDTO> listBuyingCarViewDTO = resultDTO.getContent();

        pageBuyingCarViewDTO.setListBuyingCarDTO(listBuyingCarViewDTO);

        return pageBuyingCarViewDTO;
    }
}
