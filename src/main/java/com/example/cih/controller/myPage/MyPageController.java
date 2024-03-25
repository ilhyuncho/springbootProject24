package com.example.cih.controller.myPage;


import com.example.cih.dto.PageRequestDTO;
import com.example.cih.dto.PageResponseDTO;
import com.example.cih.dto.car.CarDTO;
import com.example.cih.dto.car.CarRegisterDTO;
import com.example.cih.service.car.CarService;
import com.example.cih.service.car.UserCarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/myPage")
@RequiredArgsConstructor
@Log4j2
public class MyPageController {

    private final UserCarService userCarService;

    @GetMapping("/myCarInfo")
    public void myCarInfo(){

    }

    @GetMapping("/carRegister")
    public String list(){

//        PageResponseDTO<CarDTO> responseDTO = carService.list(pageRequestDTO);
//        model.addAttribute("responseDTO", responseDTO);

        return "/myPage/carRegister";
    }
    @PostMapping("/carRegister")
    public String register(@Valid CarRegisterDTO carRegisterDTO, BindingResult bindingResult) throws BindException {

        if(bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        Long bno = userCarService.register(carRegisterDTO);


        return "redirect:/myPage/myCarInfo";
    }



}
