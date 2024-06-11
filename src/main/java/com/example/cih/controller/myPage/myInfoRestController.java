package com.example.cih.controller.myPage;

import com.example.cih.domain.user.User;
import com.example.cih.dto.user.UserMissionReqDTO;
import com.example.cih.dto.user.UserMissionResDTO;
import com.example.cih.service.user.UserMissionService;
import com.example.cih.service.user.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/myInfo")
@RequiredArgsConstructor
@Log4j2
@PreAuthorize("hasRole('USER')")
public class myInfoRestController {

    private final UserMissionService userMissionService;
    private final UserService userService;

    @ApiOperation(value = "나의 포인트 정보 조회", notes = "")
    @GetMapping("/myPoint")
    public List<UserMissionResDTO> getMyPoint(UserMissionReqDTO userMissionReqDTO,
            Principal principal){

        log.error("나의 포인트 정보 조회 : " + userMissionReqDTO);

        User user = userService.findUser(principal.getName());

        List<UserMissionResDTO> listUserMission = userMissionService.getListUserMission(principal.getName());

        return listUserMission;
    }


}
