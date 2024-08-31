package com.example.cih.controller.myPage;

import com.example.cih.domain.user.User;
import com.example.cih.dto.PageRequestDTO;
import com.example.cih.dto.PageResponseDTO;
import com.example.cih.dto.user.UserAlarmDTO;
import com.example.cih.service.user.UserAlarmService;
import com.example.cih.service.user.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.*;
import java.security.Principal;


@RestController
@RequestMapping("/alarm")
@RequiredArgsConstructor
@Log4j2
@PreAuthorize("hasRole('USER')")
public class AlarmRestController {

    private final UserAlarmService userAlarmService;
    private final UserService userService;

    @ApiOperation(value = "알림 내역 조회", notes = "")
    @GetMapping("/list")
    public PageResponseDTO<UserAlarmDTO> getUserAlarm(PageRequestDTO pageRequestDTO,
                                           Principal principal){

        User user = userService.findUser(principal.getName());

        return userAlarmService.getAlarmInfo(pageRequestDTO, user);
    }



}
