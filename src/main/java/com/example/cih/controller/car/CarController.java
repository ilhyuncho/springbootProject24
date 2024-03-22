package com.example.cih.controller.car;


import com.example.cih.domain.car.Car;
import com.example.cih.dto.board.BoardResponseDTO;
import com.example.cih.dto.car.CarResponseDTO;
import com.example.cih.service.car.CarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RequestMapping("/dashBoard")
@RequiredArgsConstructor
@Log4j2
public class CarController {
    private final CarService carService;

    @GetMapping("/carList")
    public void list(Model model){
        log.error("CarController list()~~~~~~~~~~");

        List<CarResponseDTO> list = carService.list();

        list.forEach(log::error);
        model.addAttribute("list", list);
    }

}
