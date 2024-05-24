package com.example.cih.controller.history;


import com.example.cih.domain.user.User;
import com.example.cih.dto.statistics.StatisticsReqDTO;
import com.example.cih.dto.statistics.StatisticsResDTO;
import com.example.cih.service.carConsumable.CarStatisticsService;
import com.example.cih.service.user.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/statistics")
@RequiredArgsConstructor
@Log4j2
@PreAuthorize("hasRole('USER')")
public class StatisticsRestController {

    private final UserService userService;
    private final CarStatisticsService carStatisticsService;

    @ApiOperation(value = "내차 월별 지출 내역 조회", notes = "")
    @GetMapping("/consume")
    public List<StatisticsResDTO> getConsume(@Valid StatisticsReqDTO satisticsReqDTO,
                                             BindingResult bindingResult,
                                             Principal principal){

        log.error("statistics-consume : " + satisticsReqDTO.getCarId());

        User user = userService.findUser(principal.getName());

        List<StatisticsResDTO> listDto = carStatisticsService.getStatisticsConsume(satisticsReqDTO.getCarId());

        return listDto;
    }
}
