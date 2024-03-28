package com.example.cih.controller.car;


import com.example.cih.domain.car.Car;
import com.example.cih.dto.PageRequestDTO;
import com.example.cih.dto.PageResponseDTO;
import com.example.cih.dto.car.CarDTO;
import com.example.cih.dto.car.CarRegisterDTO;
import com.example.cih.service.car.CarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/dashBoard")
@RequiredArgsConstructor
@Log4j2
public class CarController {
    private final CarService carService;

    @GetMapping("/carInfo")
    public String carInfo(Long carId, Model model){

        log.error("carInfo=============== : " + carId);

        CarDTO carDTO = carService.readOne(carId);

        log.info("get-read:" + carDTO);

        model.addAttribute("responseDTO", carDTO);

        return "/dashBoard/carInfo";
    }

    @GetMapping("/carList")
    public String list(@ModelAttribute("pageRequestDto") PageRequestDTO pageRequestDTO, Model model){

        PageResponseDTO<CarDTO> responseDTO = carService.list(pageRequestDTO);
        model.addAttribute("responseDTO", responseDTO);

        return "/dashBoard/carList";
    }
    @GetMapping("/carSearch")
    public String carSearch(@ModelAttribute("pageRequestDto") PageRequestDTO pageRequestDTO, Model model){

        PageResponseDTO<CarDTO> responseDTO = carService.searchCarByKeyword(pageRequestDTO);
        model.addAttribute("responseDTO", responseDTO);

        return "/dashBoard/carList";
    }

}
