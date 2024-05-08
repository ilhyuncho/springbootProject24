package com.example.cih.controller.sellingCar;



import com.example.cih.dto.PageRequestDTO;
import com.example.cih.dto.sellingCar.SellingCarViewDTO;
import com.example.cih.service.sellingCar.SellingCarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/sellingCar")
@RequiredArgsConstructor
@Log4j2
@PreAuthorize("hasRole('USER')")
public class SellingCarController {

    private final SellingCarService sellingCarService;

    @GetMapping("/list")
    public String userCarList(PageRequestDTO pageRequestDTO, String userName,
                              Model model){

        List<SellingCarViewDTO> listSellingCar = sellingCarService.getListSellingCar();

        model.addAttribute("list", listSellingCar);

        return "/sellingCar/sellingCarList";
    }

}
