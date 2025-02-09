package com.example.cih.controller.myPage;

import com.example.cih.domain.reference.RefCarConsumable;
import com.example.cih.domain.user.User;
import com.example.cih.dto.car.CarConsumableResDTO;
import com.example.cih.dto.car.CarConsumableDetailResDTO;
import com.example.cih.service.carConsumable.CarConsumableService;
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
    public String get(@ModelAttribute("carId") Long carId, Model model){

        List<CarConsumableResDTO> listCarConsumableResDTO = carConsumableService.getListConsumableInfo(carId);

        model.addAttribute("listDTO", listCarConsumableResDTO);

        return "/consumable/consumableInfo";
    }

    @ApiOperation(value = "소모품 히스토리", notes = "")
    @GetMapping("/history")
    public String history(@ModelAttribute("carId") Long carId, Long refConsumableId,
                      String memberId, Model model){

        User user = userService.findUser(memberId);

        RefCarConsumable refCarConsumable =
                carConsumableService.getRefCarConsumableInfo(refConsumableId);

        List<CarConsumableDetailResDTO> listDTO = carConsumableService.getConsumableDetail(carId, refConsumableId);

        model.addAttribute("listDTO", listDTO);
        model.addAttribute("repairName", refCarConsumable.getName());

        return "/consumable/history";
    }

    @ApiOperation(value = "소모품 내역 수정 페이지", notes = "")
    @GetMapping("/modify")
    public String modify(@ModelAttribute("consumableId") Long consumableId,
                          Model model){

        CarConsumableDetailResDTO consumableInfo = carConsumableService.getConsumableInfo(consumableId);

        model.addAttribute("responseDTO", consumableInfo);

        return "/consumable/modify";
    }



}
