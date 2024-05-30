package com.example.cih.controller.sellingCar;



import com.example.cih.dto.PageRequestDTO;
import com.example.cih.dto.PageResponseDTO;
import com.example.cih.dto.sellingCar.SellingCarViewDTO;
import com.example.cih.service.sellingCar.SellingCarService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/sellingCar")
@RequiredArgsConstructor
@Log4j2

public class SellingCarController {

    private final SellingCarService sellingCarService;

    @ApiOperation(value = "판매 차량 리스트 전달", notes = "[판매 차량 조회] 클릭시")
    @GetMapping("/list")
    @PreAuthorize("isAuthenticated()")  // 로그인한 사용자만 조회, @PreAuthorize("hasRole('USER')") 과 다름
    public String getSellingCarList(@ModelAttribute("pageRequestDto") PageRequestDTO pageRequestDTO,
                              Model model){

        PageResponseDTO<SellingCarViewDTO> listSellingCar = sellingCarService.getListSellingCar(pageRequestDTO);

        model.addAttribute("responseDTO", listSellingCar);

        return "/sellingCar/sellingCarList";
    }

}
