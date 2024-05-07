package com.example.cih.controller.myPage;


import com.example.cih.common.fileHandler.FileHandler;
import com.example.cih.domain.car.Car;
import com.example.cih.domain.car.Projection;
import com.example.cih.dto.PageRequestDTO;
import com.example.cih.dto.car.CarInfoDTO;
import com.example.cih.dto.car.CarViewDTO;
import com.example.cih.dto.user.UserDTO;
import com.example.cih.service.car.UserCarService;
import com.example.cih.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.nio.file.Files;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/myPage")
@RequiredArgsConstructor
@Log4j2
@PreAuthorize("hasRole('USER')")
public class MyPageController {

    @Value("${com.cih.upload.path}")
    private String uploadPath;

    private final UserCarService userCarService;
    private final UserService userService;

    private final ModelMapper modelMapper;

    private final FileHandler fileHandler;

    @GetMapping("/userCarList")
    public String userCarList(PageRequestDTO pageRequestDTO, String userName,
                              Model model){

        UserDTO userDTO = userService.findUserDTO(userName);

        List<CarViewDTO> listCarDTO = userCarService.readMyCarList(pageRequestDTO, userDTO.getUserName());

        model.addAttribute("list", listCarDTO);

        return "/myPage/userCarList";
    }
    @GetMapping("/userCarRegister")
    public String getRegister(){
        return "/myPage/userCarRegister";
    }

    @PostMapping(value="/userCarRegister")
    public String register(@Valid CarInfoDTO carInfoDTO,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes,
                           Principal principal ){    // 임시로 다른 인증 정보 받아오는 법 확인해 보자 ( @AuthenticationPrincipal )

        log.error("carInfoDTO : " + carInfoDTO);
        log.error("user : " + principal.getName());

        if(bindingResult.hasErrors()) {
            // throw new BindException(bindingResult);
            // 바로 에러 처리 하지 말고.. 다시 입력창으로 redirect 시키고... 팝업 노출
            bindingResult.getAllErrors().forEach(log::error);

            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/myPage/userCarRegister";
        }

        Long carId = userCarService.register(principal.getName(), carInfoDTO, null);

        redirectAttributes.addFlashAttribute("result", carId);
        redirectAttributes.addAttribute("userName", principal.getName());
        return "redirect:/myPage/userCarList";
        //return "redirect:/myPage/userCarList?userName=" + principal.getName();
    }
    @GetMapping({"/userCarDetailInfo", "/userCarModify"})
    public String userCarDetailInfo(HttpServletRequest request,
                                    PageRequestDTO pageRequestDTO, String userName,
                                    @RequestParam("carId") Long carId,
                                    Model model){

        String requestURI = request.getRequestURI();

        UserDTO userDTO = userService.findUserDTO(userName);

        CarViewDTO CarViewDTO = userCarService.readMyCarDetailInfo(pageRequestDTO, userDTO.getUserName(), carId);

        model.addAttribute("responseDTO", CarViewDTO);
        model.addAttribute("userName", userName);

        return requestURI;
    }

    @PostMapping("/userCarModify")
    public String userCarModify(PageRequestDTO pageRequestDTO,
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
            return "redirect:/myPage/userCarDetailInfo?" + link;
        }

        userCarService.modifyMyCar(carInfoDTO);

        redirectAttributes.addFlashAttribute("result", "modified");
        redirectAttributes.addAttribute("carId", carInfoDTO.getCarId());
        redirectAttributes.addAttribute("userName", principal.getName());

        return "redirect:/myPage/userCarDetailInfo?" + link;
    }
    @GetMapping("/userCarSummaryInfo")
    public String userCarSummaryInfo(PageRequestDTO pageRequestDTO, String userName,
                                     Model model){

        log.error("userName: " + userName);

        UserDTO userDTO = userService.findUserDTO(userName);
        log.error("userDTO: " + userDTO);

        // Projection 타입으로 리턴
        List<Projection.CarSummary> carSummaries = userCarService.readMyCarSummaryList(pageRequestDTO, userDTO.getUserName());

        model.addAttribute("list", carSummaries);

        return "/myPage/userCarSummaryInfo";
    }
    @GetMapping("/userCarRead")
    public String userCarRead(PageRequestDTO pageRequestDTO
                             ,@RequestParam("carId") Car car        // 도메인 클래스 컨버터 기능
                             ,Model model){
        
        // 이미 Param에서 Car 엔티티 정보 로딩 됨.. 단 조회 용으로 만
        // 그리고 이기능은 많이 쓰이지 않는다
        //CarInfoDTO carInfoDTO = carService.readOne(car.getCarId());

        CarInfoDTO carInfoDTO = modelMapper.map(car, CarInfoDTO.class);
        model.addAttribute("responseDTO", carInfoDTO);

        return "/myPage/userCarRead";
    }
    @PostMapping("/userCarDelete")
    public String userCarDelete(CarInfoDTO carInfoDTO,
                                RedirectAttributes redirectAttributes){
        log.error("remove......post: " + carInfoDTO);

        userCarService.deleteMyCar(carInfoDTO.getCarId());

        // car정보가 db에서 삭제되었다면 첨부파일 삭제
        List<String> fileNames = carInfoDTO.getFileNames();
        if(fileNames != null && fileNames.size() > 0){
            fileHandler.removeFiles(fileNames);

        }

        redirectAttributes.addFlashAttribute("result", "removed");
        redirectAttributes.addAttribute("userName","user1");

        return "redirect:/myPage/userCarList";
    }

}
