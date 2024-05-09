package com.example.cih.controller.buyingCar;



import com.example.cih.dto.car.CarInfoDTO;
import com.example.cih.service.car.CarService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/buyingCar")
@RequiredArgsConstructor
@Log4j2
@PreAuthorize("hasRole('USER')")
public class BuyingCarController {

    private final CarService carService;

    @ApiOperation(value = "[구매 희망 리스트] 조회", notes = "판매 차량 정보만 전달")
    @GetMapping("/get")
    public String get(Long carId, Model model){

        CarInfoDTO carInfoDTO = carService.readOne(carId);

        model.addAttribute("carInfoDTO", carInfoDTO);

        return "/sellingCar/buyingCarList";
    }

}
