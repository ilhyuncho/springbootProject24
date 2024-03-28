package com.example.cih.sampleCode;


import com.example.cih.service.car.CarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/Sample")
@RequiredArgsConstructor
@Log4j2
//@PreAuthorize("hasRole('USER')")
public class SampleController {
    private final CarService carService;

    @GetMapping("/Sample")
    public String Sample(Long carId, Model model){

        return "";
    }
}
