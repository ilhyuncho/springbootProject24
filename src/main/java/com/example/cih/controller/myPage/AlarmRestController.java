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

import org.springframework.ui.Model;
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

    @ApiOperation(value = "알림 세부 내용 조회", notes = "")
    @GetMapping("/{alarmId}")
    public UserAlarmDTO getAlarmDetail(@PathVariable(name="alarmId") Long alarmId
            , Principal principal){

        User user = userService.findUser(principal.getName());

        return userAlarmService.getAlarmInfo(alarmId);
    }

    @ApiOperation(value = "알림 리스트 내역 조회", notes = "")
    @GetMapping("/list")
    public PageResponseDTO<UserAlarmDTO> getUserAlarm(PageRequestDTO pageRequestDTO,
                                           Principal principal){

        User user = userService.findUser(principal.getName());

        return userAlarmService.getListAlarm(pageRequestDTO, user);
    }

    @ApiOperation(value = "새로운 알림이 있는지 조회", notes = "")
    @GetMapping("/new")
    public boolean getNewAlarm(Principal principal){

        User user = userService.findUser(principal.getName());

        return userAlarmService.isNewAlarm(user);
    }



}
