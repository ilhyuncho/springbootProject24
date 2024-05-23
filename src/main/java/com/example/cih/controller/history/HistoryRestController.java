package com.example.cih.controller.history;


import com.example.cih.domain.user.User;
import com.example.cih.dto.consumable.ConsumableRegDTO;
import com.example.cih.dto.history.HistoryCarDTO;
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
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/history")
@RequiredArgsConstructor
@Log4j2
@PreAuthorize("hasRole('USER')")
public class HistoryRestController {

    private final UserService userService;
    private final CarConsumableService carConsumableService;

    @ApiOperation(value = "내차 전체 기록 화면", notes = "")
    @GetMapping("/{carId}")
    public List<HistoryCarDTO> get(@PathVariable(name="carId") Long carId,
                                   Principal principal){

        log.error("history-get : " + carId);
        User user = userService.findUser(principal.getName());

        List<HistoryCarDTO> listHistoryCarDTO = carConsumableService.getAllHistoryList(carId);

        return listHistoryCarDTO;
    }
    @ApiOperation(value = "내차 주유 기록 화면", notes = "")
    @GetMapping("/gasList/{carId}")
    public List<HistoryCarDTO> getGasList(@PathVariable(name="carId") Long carId,
                                          Principal principal){
        User user = userService.findUser(principal.getName());

        List<HistoryCarDTO> listHistoryCarDTO = carConsumableService.getGasHistoryList(carId);

        return listHistoryCarDTO;
    }

    @ApiOperation(value = "내차 주유 기록 추가", notes = "차 소유주가 등록")
    @PostMapping("/addGasHistory")
    public Map<String,String> postAddGasHistory(@Valid @RequestBody ConsumableRegDTO consumableRegDTO,
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

    @ApiOperation(value = "내차 정비 기록 화면", notes = "")
    @GetMapping("/repairList/{carId}")
    public List<HistoryCarDTO> getRepairList(@PathVariable(name="carId") Long carId,
                                             Principal principal){
        User user = userService.findUser(principal.getName());

        List<HistoryCarDTO> listHistoryCarDTO = carConsumableService.getRepairHistoryList(carId);

        return listHistoryCarDTO;
    }

    @ApiOperation(value = "내차 정비 기록 추가", notes = "차 소유주가 등록")
    @PostMapping("/addRepairHistory")
    public Map<String,String> postAddRepairHistory(@Valid @RequestBody ConsumableRegDTO consumableRegDTO,
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
