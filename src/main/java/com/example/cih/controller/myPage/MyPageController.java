package com.example.cih.controller.myPage;


import com.example.cih.common.handler.FileHandler;
import com.example.cih.domain.car.Car;
import com.example.cih.domain.user.User;
import com.example.cih.dto.PageRequestDTO;
import com.example.cih.dto.buyingCar.BuyingCarViewDTO;
import com.example.cih.dto.car.CarInfoReqDTO;
import com.example.cih.dto.car.CarViewResDTO;
import com.example.cih.service.buyingCar.BuyingCarService;
import com.example.cih.service.car.UserCarService;
import com.example.cih.service.user.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/myPage")
@RequiredArgsConstructor
@Log4j2
@PreAuthorize("hasRole('USER')")
public class MyPageController {

    private final UserCarService userCarService;
    private final BuyingCarService buyingCarService;
    private final UserService userService;

    private final FileHandler fileHandler;


    @ApiOperation(value = "보유 차 리스트 조회", notes = "")
    @GetMapping("/carList")
    //@PreAuthorize("principal.username != #userName")
    public String getCarList(String memberId, Model model){

        User user = userService.findUser(memberId);

        List<CarViewResDTO> listCarDTO = userCarService.readMyCarList(user);

        model.addAttribute("list", listCarDTO);

        listCarDTO.forEach(log::error);

        return "/myPage/carList";
    }
    @ApiOperation(value = "차 등록 페이지로 이동", notes = "")
    @GetMapping("/carRegister")
    public String getCarRegister(){
        return "/myPage/carRegister";
    }

    @ApiOperation(value = "차 세부 정보 페이지로 이동", notes = "")
    @GetMapping({"/carDetail", "/carModify"})
    public String getCarDetailOrModify(HttpServletRequest request,
                                       String memberId,
                                       @RequestParam("carId") Long carId,
                                       //@RequestParam(required = false) Long carId,   -> 비 필수 값 지정
                                       Model model){

        User user = userService.findUser(memberId);

        CarViewResDTO carViewResDTO = userCarService.readMyCarDetailInfo(user, carId);

        model.addAttribute("responseDTO", carViewResDTO);

        return request.getRequestURI();
    }

    @ApiOperation(value = "차 정보 삭제", notes = "")
    @PostMapping("/deleteCar")
    public String postDeleteCar(CarInfoReqDTO carInfoReqDTO,
                                RedirectAttributes redirectAttributes,
                                Principal principal ){

        userCarService.deleteMyCar(carInfoReqDTO.getCarId());

        // car 정보가 db에서 삭제되었다면 첨부파일 삭제
        List<String> fileNames = carInfoReqDTO.getFileNames();
        if(fileNames != null && fileNames.size() > 0){
            fileHandler.removeFiles(fileNames);
        }

        redirectAttributes.addFlashAttribute("result", "removed");

        return "redirect:/myPage/carList?memberId=" + principal.getName();
    }

    @ApiOperation(value = "차 정보 읽기", notes = "도메인 클래스 컨버터 기능 확인용")
    @GetMapping("/readCar")
    public String getCar(PageRequestDTO pageRequestDTO
            ,@RequestParam("carId") Car car        // 도메인 클래스 컨버터 기능
            ,Model model){

        // 이미 Param에서 Car 엔티티 정보 로딩 됨.. 단 조회 용으로 만
        // 그리고 이기능은 많이 쓰이지 않는다
        //CarInfoDTO carInfoDTO = carService.readOne(car.getCarId());
        return "carRead";
    }

    @ApiOperation(value = "차 구매 진행 내역 조회", notes = "")
    @GetMapping("/carOrderList")
    public String carOrderList(@ModelAttribute("pageRequestDto") PageRequestDTO pageRequestDTO,
                       Model model, Principal principal ){

        User user = userService.findUser(principal.getName());

        List<BuyingCarViewDTO> listBuyingCarViewDTO = buyingCarService.getListBuyingCarInfo(user);

        listBuyingCarViewDTO.forEach(log::error);

        model.addAttribute("listBuyingCarDTO", listBuyingCarViewDTO);

        return "/myPage/carOrderList";
    }

    @ApiOperation(value = "차 구매 진행 내역 조회", notes = "")
    @GetMapping("/alarmInfo")
    public String alarmInfo(@ModelAttribute("pageRequestDto") PageRequestDTO pageRequestDTO,
                               Model model, Principal principal ){

        User user = userService.findUser(principal.getName());

        List<BuyingCarViewDTO> listBuyingCarViewDTO = buyingCarService.getListBuyingCarInfo(user);

        listBuyingCarViewDTO.forEach(log::error);

        model.addAttribute("listBuyingCarDTO", listBuyingCarViewDTO);

        return "/myPage/alarmInfo";
    }



}
