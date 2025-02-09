package com.example.cih.controller.sellingCar;



import com.example.cih.domain.user.User;
import com.example.cih.dto.PageRequestDTO;
import com.example.cih.dto.PageRequestExtDTO;
import com.example.cih.dto.PageResponseDTO;
import com.example.cih.dto.car.CarViewResDTO;
import com.example.cih.dto.sellingCar.SellingCarResDTO;
import com.example.cih.service.car.CarService;
import com.example.cih.service.sellingCar.SellingCarService;
import com.example.cih.service.user.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/sellingCar")
@RequiredArgsConstructor
@Log4j2

public class SellingCarController {

    private final CarService carService;
    private final UserService userService;
    private final SellingCarService sellingCarService;

    @ApiOperation(value = "판매 차량 리스트 전달", notes = "[판매 차량 조회] 클릭시")
    @GetMapping("/list")
    //@PreAuthorize("isAuthenticated()")  // 로그인한 사용자만 조회, @PreAuthorize("hasRole('USER')") 과 다름
    public String getSellingCarList(@ModelAttribute("pageRequestDto") PageRequestExtDTO pageRequestExtDT,
                              Model model){

        log.error(pageRequestExtDT);

        PageResponseDTO<SellingCarResDTO> listSellingCar = sellingCarService.getListSellingCar(pageRequestExtDT);

        log.error(listSellingCar);

        model.addAttribute("responseDTO", listSellingCar);

        return "/sellingCar/sellingCarList";
    }

    @ApiOperation(value = "[판매 차량 정보 요청]", notes = "[차량 주문]-[조회] 시 호출, 내 차가 아님")
    @GetMapping("/{carId}")
    public String getCarInfo(@PathVariable(name="carId") Long carId
            ,Model model, Principal principal){

        if(principal != null){
            log.error(principal.getName());
        }
        else{
            log.error("principal is null!!!!!!!!");
        }

       // userCarService.readMyCarDetailInfo( principal.getName(), carId);
        CarViewResDTO carViewResDTO = carService.readOne(carId);

        model.addAttribute("carViewDTO", carViewResDTO);

        String urlPage = null;
        if(carViewResDTO.getSellType().getType().equals("auctionType")){
            urlPage = "auctionDetail";
        }
        else if(carViewResDTO.getSellType().getType().equals("consultType")){
            urlPage = "consultDetail";
        }
        else{
            // 에러 처리
            return "redirect:/myPage/carOrderList";
        }
        return "/sellingCar/" + urlPage;
    }
    @ApiOperation(value = "[판매 차량 정보 요청]", notes = "판매 차량 정보만 전달")
    @GetMapping("/view/{sellingCarId}")
    public String getCarView(@PathVariable(name="sellingCarId") Long sellingCarId
            ,Model model, Principal principal){

        User user = null;
        if(principal != null){
            user = userService.findUser(principal.getName());
        }

        SellingCarResDTO sellingCarResDTO = sellingCarService.getSellingCarInfo(user, sellingCarId);

        model.addAttribute("responseDTO", sellingCarResDTO);

        log.error(sellingCarResDTO);

        return "/sellingCar/sellingCarView";
    }

}
