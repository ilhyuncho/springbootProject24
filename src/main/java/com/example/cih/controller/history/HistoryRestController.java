package com.example.cih.controller.history;


import com.example.cih.domain.user.User;
import com.example.cih.dto.car.CarConsumableRegDTO;
import com.example.cih.dto.history.HistoryCarResDTO;
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
    public List<HistoryCarResDTO> get(@PathVariable(name="carId") Long carId,
                                      Principal principal){

        return carConsumableService.getListAllHistory(carId);
    }
    @ApiOperation(value = "내차 주유 기록 화면", notes = "")
    @GetMapping("/gasList/{carId}")
    public List<HistoryCarResDTO> getGasList(@PathVariable(name="carId") Long carId,
                                             Principal principal){
        User user = userService.findUser(principal.getName());

        return carConsumableService.getListGasHistory(carId);
    }

    @ApiOperation(value = "내차 정비 기록 화면", notes = "")
    @GetMapping("/repairList/{carId}")
    public List<HistoryCarResDTO> getRepairList(@PathVariable(name="carId") Long carId,
                                                Principal principal){
        User user = userService.findUser(principal.getName());

        return carConsumableService.getListRepairHistory(carId);
    }

    @ApiOperation(value = "내차 주유 or 정비 기록 추가", notes = "차 소유주가 등록")
    @PostMapping("/addHistory")
    public Map<String,String> postAddGasHistory(@Valid @RequestBody CarConsumableRegDTO carConsumableRegDTO,
                                              BindingResult bindingResult,
                                              Principal principal ) throws BindException {
        if(bindingResult.hasErrors()){
            log.error("has errors.....");
            throw new BindException(bindingResult);
        }

        carConsumableService.registerConsumable(carConsumableRegDTO);

        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("result", "success");
        return resultMap;
    }
}
