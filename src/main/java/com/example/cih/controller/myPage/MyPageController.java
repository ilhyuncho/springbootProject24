package com.example.cih.controller.myPage;


import com.example.cih.dto.PageRequestDTO;
import com.example.cih.dto.PageResponseDTO;
import com.example.cih.dto.car.CarInfoDTO;
import com.example.cih.dto.car.CarSpecDTO;
import com.example.cih.service.car.CarService;
import com.example.cih.service.car.UserCarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/myPage")
@RequiredArgsConstructor
@Log4j2
//@PreAuthorize("hasRole('USER')")
public class MyPageController {

    private final UserCarService userCarService;

    @GetMapping("/carInfo")
    public String carInfo(PageRequestDTO pageRequestDTO, String UserId, Model model){

        // 임시
        Long tempUserId = 3L;
        List<CarInfoDTO> listCarDTO = userCarService.readMyCarInfo(pageRequestDTO, tempUserId);

        listCarDTO.forEach(log::error);

        model.addAttribute("list", listCarDTO);

        return "/myPage/myCarInfo";
    }

    @GetMapping("/carRegister")
    public String list(){

        return "/myPage/carRegister";
    }

    @PostMapping("/carRegister")
    public String register(@Valid CarSpecDTO carSpecDTO, BindingResult bindingResult,
                           RedirectAttributes redirectAttributes) throws BindException {

        if(bindingResult.hasErrors()) {
           // throw new BindException(bindingResult);
            // 바로 에러 처리 하지 말고.. 다시 입력창으로 redirect 시키고... 팝업 노출

            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/myPage/carRegister";
        }

        Long bno = userCarService.register(carSpecDTO);
        return "redirect:/myPage/myCarInfo";
    }



}
