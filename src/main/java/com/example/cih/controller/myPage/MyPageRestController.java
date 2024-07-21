package com.example.cih.controller.myPage;


import com.example.cih.domain.reference.RefCarSample;
import com.example.cih.dto.car.CarInfoReqDTO;
import com.example.cih.dto.car.CarKmUpdateReqDTO;
import com.example.cih.dto.car.CarRegisterReqDTO;
import com.example.cih.service.car.UserCarService;
import com.example.cih.service.reference.RefCarSampleService;
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
@RequestMapping("/myPage")
@RequiredArgsConstructor
@Log4j2
@PreAuthorize("hasRole('USER')")
public class MyPageRestController {

    private final UserCarService userCarService;
    private final RefCarSampleService refCarSampleService;

    @ApiOperation(value = "내차 누적 주행거리 갱신", notes = "차 소유주가 등록")
    @PostMapping("/updateCarKm")
    public Map<String,String> postUpdateCarKm(@Valid @RequestBody CarKmUpdateReqDTO carKmUpdateReqDTO,
                                          BindingResult bindingResult,
                                          Principal principal ) throws BindException {

        if(bindingResult.hasErrors()){
            log.error("has errors.....");
            throw new BindException(bindingResult);
        }

        userCarService.modifyMyCarKm(carKmUpdateReqDTO);
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("result", "success");

        return resultMap;
    }

    @ApiOperation(value = "나의 차량 조회", notes = "")
    @GetMapping("/findMyCar")
    public RefCarSample findMyCar(String carNumber, Principal principal){

        log.error("carNumber : " + carNumber);

        RefCarSample myCar = refCarSampleService.findMyCar(carNumber);

        return myCar;
    }

    @ApiOperation(value = "차 등록 (post)", notes = "")
    @PostMapping(value="/carRegister")
    public Map<String,String> postCarRegister(@RequestBody CarRegisterReqDTO carRegisterReqDTO,
                                     Principal principal ){

        log.error("carRegisterReqDTO : " + carRegisterReqDTO);

        Long carId = userCarService.registerMyCar(principal.getName(), carRegisterReqDTO.getCarNumber());

        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("result", "success");
        resultMap.put("carId", carId.toString());

        return resultMap;
    }

    @ApiOperation(value = "차 세부 변경 (post)", notes = "")
    @PostMapping("/carModify")
    public Map<String,String> postCarModify(@RequestBody CarInfoReqDTO carInfoReqDTO,
                                Principal principal ){
        log.error("car modify post...." + carInfoReqDTO);

        userCarService.modifyMyCar(carInfoReqDTO);

        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("result", "success");

        return resultMap;
    }

}
