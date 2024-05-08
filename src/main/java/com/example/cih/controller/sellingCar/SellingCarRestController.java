package com.example.cih.controller.sellingCar;

import com.example.cih.dto.sellingCar.SellingCarRegDTO;
import com.example.cih.dto.sellingCar.SellingCarViewDTO;
import com.example.cih.service.sellingCar.SellingCarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/sellingCar")
@RequiredArgsConstructor
@Log4j2
public class SellingCarRestController {

    private final SellingCarService sellingCarService;

    @PostMapping("/register")
    public Map<String,String> registerSellingCar(@Valid @RequestBody SellingCarRegDTO sellingCarRegDTO,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes,
                                Principal principal ) throws BindException {
        log.error("registerSellingCar post...." + sellingCarRegDTO);

        if(bindingResult.hasErrors()){
            log.error("has errors.....");
            throw new BindException(bindingResult);
        }

        sellingCarService.registerSellingCar(principal.getName(), sellingCarRegDTO);

        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("result", "success");

        return resultMap;
    }

    @GetMapping("/get")
    public SellingCarViewDTO getSellingCar(Long sellingCarId, Principal principal){

        SellingCarViewDTO sellingCarViewDTO = sellingCarService.getSellingCar(sellingCarId);

        return sellingCarViewDTO;
    }


}
