package com.example.cih.controller.car;


import com.example.cih.domain.car.Car;
import com.example.cih.dto.PageRequestDTO;
import com.example.cih.dto.PageResponseDTO;
import com.example.cih.dto.car.CarDTO;
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

    @GetMapping("/carList")
    public String list(@ModelAttribute("pageRequestDto") PageRequestDTO pageRequestDTO, Model model){
        log.error("CarController list()~~~~~~~~~~");
        log.error("pageRequestDto: " + pageRequestDTO);


        PageResponseDTO<Car> list = carService.list(pageRequestDTO);
        model.addAttribute("list", list.getDtoList());

        return "/dashBoard/carList";
    }
    @GetMapping("/carSearch")
    public String carSearch(@ModelAttribute("pageRequestDto") PageRequestDTO pageRequestDTO, Model model){
        log.error("CarController list()~~~~~~~~~~");
        log.error("pageRequestDto: " + pageRequestDTO);


        PageResponseDTO<Car> list = carService.searchCarByKeyword(pageRequestDTO);
        model.addAttribute("list", list.getDtoList());

        return "/dashBoard/carList";
    }

    @GetMapping("/carInfo")
    public String carInfo(Long carId,  PageRequestDTO pageRequestDTO, Model model){
        log.error("CarController carInfo()~~~~~~~~~~");
        log.error("carId: " + carId);
        log.error("pageRequestDTO : " + pageRequestDTO);


        CarDTO carDTO = carService.readOne(carId);
        model.addAttribute("dto", carDTO);

        return "/dashBoard/carInfo";
    }

}
