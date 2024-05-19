package com.example.cih.controller.myPage;

import com.example.cih.dto.consumable.ConsumableRegDTO;
import com.example.cih.service.car.CarConsumableService;
import com.example.cih.service.user.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/consumable")
@RequiredArgsConstructor
@Log4j2
@PreAuthorize("hasRole('USER')")
public class ConsumableRestController {

    private final CarConsumableService carConsumableService;
    private final UserService userService;

    @ApiOperation(value = "소모품 교환 날짜 등록", notes = "차 소유주가 등록")
    @PostMapping("/register")
    public Map<String,String> postRegisterSellingCar(@Valid @RequestBody ConsumableRegDTO consumableRegDTO,
                                                     BindingResult bindingResult,
                                                     Principal principal ) throws BindException {

        if(bindingResult.hasErrors()){
            log.error("has errors.....");
            throw new BindException(bindingResult);
        }

        carConsumableService.registerConsumable(principal.getName(), consumableRegDTO);

        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("result", "success");

        return resultMap;
    }


}
