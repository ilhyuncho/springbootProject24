package com.example.cih.controller.myPage;


import com.example.cih.domain.reference.RefCarSample;
import com.example.cih.domain.user.User;
import com.example.cih.dto.PageRequestDTO;
import com.example.cih.dto.car.CarKmUpdateDTO;
import com.example.cih.dto.car.CarRegDTO;
import com.example.cih.dto.user.UserMissionListResDTO;
import com.example.cih.dto.user.UserMissionReqDTO;
import com.example.cih.dto.user.UserMissionResDTO;
import com.example.cih.service.car.UserCarService;
import com.example.cih.service.reference.RefCarSampleService;
import com.example.cih.service.user.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public Map<String,String> postUpdateCarKm(@Valid @RequestBody CarKmUpdateDTO carKmUpdateDTO,
                                          BindingResult bindingResult,
                                          Principal principal ) throws BindException {

        if(bindingResult.hasErrors()){
            log.error("has errors.....");
            throw new BindException(bindingResult);
        }

        userCarService.modifyMyCarKm(carKmUpdateDTO);
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
    public Map<String,String> postCarRegister(@RequestBody CarRegDTO carRegDTO,
                                     Principal principal ){

        log.error("carRegDTO : " + carRegDTO);

        Long carId = userCarService.register(principal.getName(), carRegDTO.getCarNumber());

        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("result", "success");
        resultMap.put("carId", carId.toString());

        return resultMap;
    }

}
