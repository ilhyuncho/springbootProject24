package com.example.cih.controller.myPage;


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
    private final UserCreditService userCreditService;
    private final UserService userService;
    private final CarService carService;

    private final ModelMapper modelMapper;

    @GetMapping("/myCarInfo")
    public String myCarInfo(PageRequestDTO pageRequestDTO, String userName, Model model){

        log.error("userName: " + userName);

        UserDTO userDTO = userService.findByUserName(userName);
        log.error("userDTO: " + userDTO);

        List<CarInfoDTO> listCarDTO = userCarService.readMyCarList(pageRequestDTO, userDTO.getUserName());

        model.addAttribute("list", listCarDTO);

        return "/myPage/myCarInfo";
    }

    @GetMapping("/myCarRead")
    public String myCarRead(PageRequestDTO pageRequestDTO, Long carId, Model model){

        CarInfoDTO carInfoDTO = carService.readOne(carId);

        log.info("get-read:" + carInfoDTO);

        model.addAttribute("responseDTO", carInfoDTO);

        return "/myPage/myCarRead";
    }

    @GetMapping("/carRegister")
    public String list(){

        return "/myPage/carRegister";
    }

    @PostMapping(value="/carRegister")
    public String register(@Valid CarSpecDTO carSpecDTO,
                           Principal principal,     // 임시로 다른 인증 정보 받아오는 법 확인해 보자 ( @AuthenticationPrincipal )
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes) throws BindException {

        log.error("carSpecDTO : " + carSpecDTO);
        log.error("user : " + principal.getName());

        if(bindingResult.hasErrors()) {
           // throw new BindException(bindingResult);
            // 바로 에러 처리 하지 말고.. 다시 입력창으로 redirect 시키고... 팝업 노출

            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/myPage/carRegister";
        }

        Long bno = userCarService.register(principal.getName(), carSpecDTO, null);

        return "redirect:/dashBoard/carList";
    }

    @GetMapping("/myCreditInfo")
    public String myCreditInfo(PageRequestDTO pageRequestDTO, String userName, Model model){

        log.error("myCreditInfo: userName: " + userName);

        UserCreditDTO userCreditDTO = null;

        UserDTO userDTO = userService.findByUserName(userName);
        if( userDTO != null) {

            log.error("userDTO: " + userDTO);

            User user = modelMapper.map(userDTO, User.class);

            userCreditDTO = userCreditService.readCreditInfo(user);

            log.info("get-read:" + userCreditDTO);
        }

        model.addAttribute("responseDTO", userCreditDTO);

        return "/myPage/myCreditInfo";
    }

    @GetMapping("/creditRegister")
    public String myCredit(PageRequestDTO pageRequestDTO, Long carId, Model model){

        return "/myPage/creditRegister";
    }
    @PostMapping(value="/creditRegister")
    public String myCredit(@Valid UserCreditDTO userCreditDTO,
                           Principal principal,     // 임시로 다른 인증 정보 받아오는 법 확인해 보자 ( @AuthenticationPrincipal )
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes) throws BindException {

        log.error("userCreditDTO : " + userCreditDTO);

        if(bindingResult.hasErrors()) {
            // throw new BindException(bindingResult);
            // 바로 에러 처리 하지 말고.. 다시 입력창으로 redirect 시키고... 팝업 노출

            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/myPage/carRegister";
        }

        Long bno = userCreditService.register(principal.getName(), userCreditDTO);

        return "redirect:/myPage/myCreditInfo?userName=" + principal.getName();
    }

}
