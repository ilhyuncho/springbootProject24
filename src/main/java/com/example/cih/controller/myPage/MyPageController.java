package com.example.cih.controller.myPage;


import com.example.cih.domain.car.Projection;
import com.example.cih.domain.user.User;
import com.example.cih.dto.PageRequestDTO;
import com.example.cih.dto.car.CarInfoDTO;
import com.example.cih.dto.car.CarSpecDTO;
import com.example.cih.dto.user.UserDTO;
import com.example.cih.dto.user.UserCreditDTO;
import com.example.cih.service.user.UserCreditService;
import com.example.cih.service.car.CarService;
import com.example.cih.service.car.UserCarService;
import com.example.cih.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/myPage")
@RequiredArgsConstructor
@Log4j2
@PreAuthorize("hasRole('USER')")
public class MyPageController {

    private final UserCarService userCarService;
    private final UserService userService;
    private final CarService carService;

    @GetMapping("/userCarInfo")
    public String userCarInfo(PageRequestDTO pageRequestDTO, String userName, Model model){

        log.error("userName: " + userName);

        UserDTO userDTO = userService.findByUserName(userName);
        log.error("userDTO: " + userDTO);

        List<CarInfoDTO> listCarDTO = userCarService.readMyCarList(pageRequestDTO, userDTO.getUserName());

        model.addAttribute("list", listCarDTO);

        return "/myPage/userCarInfo";
    }
    @GetMapping("/userCarSummaryInfo")
    public String userCarSummaryInfo(PageRequestDTO pageRequestDTO, String userName, Model model){

        log.error("userName: " + userName);

        UserDTO userDTO = userService.findByUserName(userName);
        log.error("userDTO: " + userDTO);

        // Projection 타입으로 리턴
        List<Projection.CarSummary> carSummaries = userCarService.readMyCarSummaryList(pageRequestDTO, userDTO.getUserName());

        model.addAttribute("list", carSummaries);

        return "/myPage/userCarSummaryInfo";
    }
    @GetMapping("/userCarRead")
    public String userCarRead(PageRequestDTO pageRequestDTO, Long carId, Model model){

        CarInfoDTO carInfoDTO = carService.readOne(carId);

        log.info("get-read:" + carInfoDTO);

        model.addAttribute("responseDTO", carInfoDTO);

        return "/myPage/userCarRead";
    }

    @GetMapping("/userCarRegister")
    public String list(){
        return "/myPage/userCarRegister";
    }

    @PostMapping(value="/userCarRegister")
    public String register(@Valid CarSpecDTO carSpecDTO,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes,
                           Principal principal    // 임시로 다른 인증 정보 받아오는 법 확인해 보자 ( @AuthenticationPrincipal )
    ) {

        log.error("carSpecDTO : " + carSpecDTO);
        log.error("user : " + principal.getName());

        if(bindingResult.hasErrors()) {
           // throw new BindException(bindingResult);
            // 바로 에러 처리 하지 말고.. 다시 입력창으로 redirect 시키고... 팝업 노출

            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/myPage/userCarRegister";
        }

        Long bno = userCarService.register(principal.getName(), carSpecDTO, null);

        return "redirect:/myPage/userCarInfo?userName=" + principal.getName();
    }
}
