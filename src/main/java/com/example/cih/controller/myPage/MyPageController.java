package com.example.cih.controller.myPage;


import com.example.cih.controller.fileUpload.UploadFileDTO;
import com.example.cih.controller.fileUpload.UploadResultDTO;
import com.example.cih.dto.PageRequestDTO;
import com.example.cih.dto.PageResponseDTO;
import com.example.cih.dto.car.CarInfoDTO;
import com.example.cih.dto.car.CarSpecDTO;
import com.example.cih.dto.user.UserDTO;
import com.example.cih.service.car.CarService;
import com.example.cih.service.car.UserCarService;
import com.example.cih.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/myPage")
@RequiredArgsConstructor
@Log4j2
@PreAuthorize("hasRole('USER')")
public class MyPageController {

    private final UserCarService userCarService;
    private final UserService userService;

    @GetMapping("/carInfo")
    public String carInfo(PageRequestDTO pageRequestDTO, String userName, Model model){

        log.error("userName: " + userName);

        UserDTO userDTO = userService.findByUserName(userName);
        log.error("userDTO: " + userDTO);

        List<CarInfoDTO> listCarDTO = userCarService.readMyCarInfo(pageRequestDTO, userDTO.getUserID());

        listCarDTO.forEach(log::error);

        model.addAttribute("list", listCarDTO);

        return "/myPage/myCarInfo";
    }

    @GetMapping("/carRegister")
    public String list(){

        return "/myPage/carRegister";
    }

    @PostMapping(value="/carRegister", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public String register(@Valid CarSpecDTO carSpecDTO,
                           UploadFileDTO uploadFileDTO,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes) throws BindException {

        log.error("carSpecDTO : " + carSpecDTO);

        if(bindingResult.hasErrors()) {
           // throw new BindException(bindingResult);
            // 바로 에러 처리 하지 말고.. 다시 입력창으로 redirect 시키고... 팝업 노출

            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/myPage/carRegister";
        }

        Long bno = userCarService.register(carSpecDTO, uploadFileDTO);

        return "redirect:/dashBoard/carList";
    }



}
