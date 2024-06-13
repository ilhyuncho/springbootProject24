package com.example.cih.controller.myPage;


import com.example.cih.common.handler.FileHandler;
import com.example.cih.domain.car.Car;
import com.example.cih.domain.car.Projection;
import com.example.cih.dto.PageRequestDTO;
import com.example.cih.dto.car.CarInfoDTO;
import com.example.cih.dto.car.CarViewDTO;
import com.example.cih.dto.user.UserDTO;
import com.example.cih.service.car.UserCarService;
import com.example.cih.service.user.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
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

    private final ModelMapper modelMapper;
    private final FileHandler fileHandler;



    @ApiOperation(value = "보유 차 리스트 조회", notes = "")
    @GetMapping("/carList")
    //@PreAuthorize("principal.username != #userName")
    public String getCarList(PageRequestDTO pageRequestDTO, String userName,
                              Model model){

        log.error("userName : " + userName);
        UserDTO userDTO = userService.findUserDTO(userName);

        List<CarViewDTO> listCarDTO = userCarService.readMyCarList(pageRequestDTO, userDTO.getUserName());

        model.addAttribute("list", listCarDTO);

        return "/myPage/carList";
    }
    @ApiOperation(value = "차 등록 페이지로 이동", notes = "")
    @GetMapping("/carRegister")
    public String getCarRegister(){
        return "/myPage/carRegisterNew";
    }

//    @ApiOperation(value = "차 등록 (post)", notes = "")
//    @PostMapping(value="/carRegister")
//    public String postCarRegister(@Valid CarInfoDTO carInfoDTO,
//                           BindingResult bindingResult,
//                           RedirectAttributes redirectAttributes,
//                           Principal principal ){    // 임시로 다른 인증 정보 받아오는 법 확인해 보자 ( @AuthenticationPrincipal )
//
//        if(bindingResult.hasErrors()) {
//            // throw new BindException(bindingResult);
//            // 바로 에러 처리 하지 말고.. 다시 입력창으로 redirect 시키고... 팝업 노출
//            bindingResult.getAllErrors().forEach(log::error);
//
//            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
//            return "redirect:/myPage/carRegister";
//        }
//
//        Long carId = userCarService.register(principal.getName(), carInfoDTO, null);
//
//        redirectAttributes.addFlashAttribute("result", carId);
//        redirectAttributes.addAttribute("userName", principal.getName());
//        return "redirect:/myPage/carList";
//        //return "redirect:/myPage/carList?userName=" + principal.getName();
//    }

    @ApiOperation(value = "차 세부 정보 페이지로 이동", notes = "")
    @GetMapping({"/carDetail", "/carModify"})
    public String getCarDetailOrModify(HttpServletRequest request,
                                    PageRequestDTO pageRequestDTO, String userName,
                                    @RequestParam("carId") Long carId,
                                    Model model){

        String requestURI = request.getRequestURI();

        UserDTO userDTO = userService.findUserDTO(userName);

        CarViewDTO carViewDTO = userCarService.readMyCarDetailInfo(userDTO.getUserName(), carId);

        model.addAttribute("responseDTO", carViewDTO);
        model.addAttribute("userName", userName);

        return requestURI;
    }

    @ApiOperation(value = "차 세부 변경 (post)", notes = "")
    @PostMapping("/carModify")
    public String postCarModify(PageRequestDTO pageRequestDTO,
                                @Valid CarInfoDTO carInfoDTO,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes,
                                Principal principal ){
        log.error("car modify post...." + carInfoDTO);

        String link = pageRequestDTO.getLink();

        if(bindingResult.hasErrors()){
            log.error("has errors.....");

            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            redirectAttributes.addAttribute("carId", carInfoDTO.getCarId());
            return "redirect:/myPage/carDetail?" + link;
        }

        userCarService.modifyMyCar(carInfoDTO);

        redirectAttributes.addFlashAttribute("result", "modified");
        redirectAttributes.addAttribute("carId", carInfoDTO.getCarId());
        redirectAttributes.addAttribute("userName", principal.getName());

        return "redirect:/myPage/carDetail?" + link;
    }
    @ApiOperation(value = "차 세부 정보 요약 페이지로 이동", notes = "")
    @GetMapping("/carSummary")
    public String getCarSummary(PageRequestDTO pageRequestDTO, String userName,
                                     Model model){

        log.error("userName: " + userName);

        UserDTO userDTO = userService.findUserDTO(userName);
        log.error("userDTO: " + userDTO);

        // Projection 타입으로 리턴
        List<Projection.CarSummary> carSummaries = userCarService.readMyCarSummaryList(pageRequestDTO, userDTO.getUserName());

        model.addAttribute("list", carSummaries);

        return "carSummary";
    }
    @ApiOperation(value = "차 정보 삭제", notes = "")
    @PostMapping("/deleteCar")
    public String postDeleteCar(CarInfoDTO carInfoDTO,
                                RedirectAttributes redirectAttributes,
                                Principal principal ){
        log.error("remove......post: " + carInfoDTO);

        userCarService.deleteMyCar(carInfoDTO.getCarId());

        // car정보가 db에서 삭제되었다면 첨부파일 삭제
        List<String> fileNames = carInfoDTO.getFileNames();
        if(fileNames != null && fileNames.size() > 0){
            fileHandler.removeFiles(fileNames);
        }

        redirectAttributes.addFlashAttribute("result", "removed");
        //redirectAttributes.addAttribute("userName","user1");
        return "redirect:/myPage/carList?userName=" + principal.getName();
    }

    @ApiOperation(value = "차 정보 읽기", notes = "도메인 클래스 컨버터 기능 확인용")
    @GetMapping("/readCar")
    public String getCar(PageRequestDTO pageRequestDTO
            ,@RequestParam("carId") Car car        // 도메인 클래스 컨버터 기능
            ,Model model){

        // 이미 Param에서 Car 엔티티 정보 로딩 됨.. 단 조회 용으로 만
        // 그리고 이기능은 많이 쓰이지 않는다
        //CarInfoDTO carInfoDTO = carService.readOne(car.getCarId());

        CarInfoDTO carInfoDTO = modelMapper.map(car, CarInfoDTO.class);
        model.addAttribute("responseDTO", carInfoDTO);

        return "carRead";
    }

}
