package com.example.cih.controller.myPage;

import com.example.cih.domain.user.User;
import com.example.cih.dto.car.CarConsumableDTO;
import com.example.cih.dto.car.CarConsumableInfoDTO;
import com.example.cih.service.car.CarConsumableService;
import com.example.cih.service.user.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/consumable")
@RequiredArgsConstructor
@Log4j2
@PreAuthorize("hasRole('USER')")
public class ConsumableController {

    private final CarConsumableService carConsumableService;
    private final UserService userService;

    @ApiOperation(value = "내차 소모품 화면", notes = "")
    @GetMapping("/info")
    public String get(@ModelAttribute("carId") Long carId,
                      String userName, Model model){

        User user = userService.findUser(userName);

        List<CarConsumableDTO> listCarConsumableDTO = carConsumableService.readOne(carId);

        model.addAttribute("listDTO", listCarConsumableDTO);

        for (CarConsumableDTO carConsumableDTO : listCarConsumableDTO) {
            log.error(carConsumableDTO.toString());
        }
        return "/consumable/info";
    }

    @ApiOperation(value = "소모품 히스토리", notes = "")
    @GetMapping("/history")
    public String history(@ModelAttribute("carId") Long carId, Long consumableId,
                      String userName, Model model){

        User user = userService.findUser(userName);

        List<CarConsumableInfoDTO> listDTO = carConsumableService.readDetailInfo(carId, consumableId);

        model.addAttribute("listDTO", listDTO);

        return "/consumable/history";
    }



}
