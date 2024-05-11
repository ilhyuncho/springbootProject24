package com.example.cih.controller.buyingCar;

import com.example.cih.dto.car.CarViewDTO;
import com.example.cih.service.car.CarService;
import com.example.cih.service.car.UserCarService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/buyingCar")
@RequiredArgsConstructor
@Log4j2
@PreAuthorize("hasRole('USER')")
public class BuyingCarController {

    private final UserCarService userCarService;

    @ApiOperation(value = "[구매 희망 리스트] 조회", notes = "판매 차량 정보만 전달")
    @GetMapping("/get")
    public String get(Long carId, Model model, Principal principal){

        CarViewDTO carViewDTO = userCarService.readMyCarDetailInfo( principal.getName(), carId);

        model.addAttribute("carViewDTO", carViewDTO);

        return "/sellingCar/buyingCarList";
    }

}
