package com.example.cih.sampleCode.temp;


import com.example.cih.service.car.CarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/UserCredit")
@RequiredArgsConstructor
@Log4j2
//@PreAuthorize("hasRole('USER')")
public class UserCreditController {
    private final UserCreditService userCreditService;

    @GetMapping("/Sample")
    public String Sample(Long carId, Model model){

        return "";
    }
}
