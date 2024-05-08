package com.example.cih.controller.buyRequest;



import com.example.cih.dto.PageRequestDTO;
import com.example.cih.dto.car.CarInfoDTO;
import com.example.cih.service.car.CarService;
import com.example.cih.service.sellingCar.BuyRequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/buyRequest")
@RequiredArgsConstructor
@Log4j2
@PreAuthorize("hasRole('USER')")
public class BuyRequestController {

    private final CarService carService;

    @GetMapping("/get")
    public String buyRequest(PageRequestDTO pageRequestDTO,
                             Long carId,
                             Model model){

        CarInfoDTO carInfoDTO = carService.readOne(carId);

        model.addAttribute("carInfoDTO", carInfoDTO);

        return "/sellingCar/buyRequestList";
    }

}
