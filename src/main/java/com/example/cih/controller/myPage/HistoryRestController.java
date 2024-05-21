package com.example.cih.controller.myPage;


import com.example.cih.domain.user.User;
import com.example.cih.dto.car.CarConsumableDTO;
import com.example.cih.service.car.CarConsumableService;
import com.example.cih.service.user.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/history")
@RequiredArgsConstructor
@Log4j2
@PreAuthorize("hasRole('USER')")
public class HistoryRestController {

    private final CarConsumableService carConsumableService;
    private final UserService userService;

    @ApiOperation(value = "내차 기록 화면", notes = "")
    @GetMapping("/{carId}")
    public List<CarConsumableDTO> get(@PathVariable(name="carId") Long carId,
                      Principal principal){

        log.error("history-get : " + carId);
        User user = userService.findUser(principal.getName());

        List<CarConsumableDTO> listCarConsumableDTO = carConsumableService.readOne(carId);

        return listCarConsumableDTO;
    }

}
