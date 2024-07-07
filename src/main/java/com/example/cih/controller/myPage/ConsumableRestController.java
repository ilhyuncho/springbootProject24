package com.example.cih.controller.myPage;

import com.example.cih.common.exception.OwnerCarNotFoundException;
import com.example.cih.domain.user.User;
import com.example.cih.dto.car.CarConsumableRegDTO;
import com.example.cih.service.carConsumable.CarConsumableService;
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
    public Map<String,String> postRegisterConsumable(@Valid @RequestBody CarConsumableRegDTO carConsumableRegDTO,
                                                     BindingResult bindingResult,
                                                     Principal principal ) throws BindException {
        if(bindingResult.hasErrors()){
            log.error("has errors.....");
            throw new BindException(bindingResult);
        }
        User user = userService.findUser(principal.getName());

        Map<String, String> resultMap = new HashMap<>();

        try{
            carConsumableService.registerConsumable(carConsumableRegDTO);
            resultMap.put("result", "success");
        }catch(Exception e){
            if (e.getClass() == OwnerCarNotFoundException.class) {
                resultMap.put("result", "fail");
                resultMap.put("message",e.getMessage());
            }
        }

        return resultMap;
    }

    @ApiOperation(value = "소모품 내역 정보 수정", notes = "차 소유주가 등록")
    @PostMapping("/modify")
    public Map<String,String> postModifyConsumable(@Valid @RequestBody CarConsumableRegDTO carConsumableRegDTO,
                                                     BindingResult bindingResult,
                                                     Principal principal ) throws BindException {
        if(bindingResult.hasErrors()){
            log.error("has errors.....");
            throw new BindException(bindingResult);
        }
        User user = userService.findUser(principal.getName());

        carConsumableService.modifyConsumable(carConsumableRegDTO);

        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("result", "success");
        return resultMap;
    }

    @ApiOperation(value = "소모품 내역 정보 삭제", notes = "차 소유주가 등록")
    @PostMapping("/remove/{consumableId}")
    public Map<String,String> postRemoveConsumable(@PathVariable Long consumableId,
                                                   Principal principal ) {

        User user = userService.findUser(principal.getName());

        carConsumableService.removeConsumable(consumableId);

        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("result", "success");
        return resultMap;
    }


}
