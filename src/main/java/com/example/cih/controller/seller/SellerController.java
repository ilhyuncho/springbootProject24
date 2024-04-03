package com.example.cih.controller.seller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/Seller")
@RequiredArgsConstructor
@Log4j2
//@PreAuthorize("hasRole('USER')")
public class SellerController {
    @GetMapping("/info")
    public String info(Long carId, Model model){

        return "";
    }

    @PostMapping("/agreement")
    public void agreement(){

    }

}
