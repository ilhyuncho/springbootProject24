package com.example.cih.controller.buyRequest;

import com.example.cih.dto.PageRequestDTO;
import com.example.cih.dto.sellingCar.BuyRequestRegDTO;
import com.example.cih.dto.sellingCar.BuyRequestViewDTO;
import com.example.cih.service.sellingCar.BuyRequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/buyRequest")
@RequiredArgsConstructor
@Log4j2
public class BuyRequestRestController {

    private final BuyRequestService buyRequestService;

    @PostMapping("/register")
    public Map<String,String> register(@Valid @RequestBody BuyRequestRegDTO buyRequestRegDTO,
                                                 BindingResult bindingResult,
                                                 RedirectAttributes redirectAttributes,
                                                 Principal principal ) throws BindException {
        log.error("registerBuyRequest post...." + buyRequestRegDTO);

        if(bindingResult.hasErrors()){
            log.error("has errors.....");
            throw new BindException(bindingResult);
        }

        buyRequestService.registerBuyRequest(principal.getName(), buyRequestRegDTO);

        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("result", "success");

        return resultMap;
    }

    @GetMapping("/list")
    public List<BuyRequestViewDTO> buyRequest(PageRequestDTO pageRequestDTO,
                             Long sellingCarId){

        log.error("sellingCarId:"+sellingCarId);

        List<BuyRequestViewDTO> listBuyRequest = buyRequestService.getListBuyRequest(sellingCarId);

        return listBuyRequest;
    }
}
