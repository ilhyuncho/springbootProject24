package com.example.cih.controller.myPage;


import com.example.cih.dto.user.UserDTO;
import com.example.cih.service.user.UserMissionService;
import com.example.cih.service.user.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/myPage")
@RequiredArgsConstructor
@Log4j2
@PreAuthorize("hasRole('USER')")
public class myInfoController {

    private final UserService userService;
    private final UserMissionService userMissionService;

    @ApiOperation(value = "나의 정보 조회", notes = "")
    @GetMapping("/myInfo")
    //@PreAuthorize("principal.username != #userName")
    public String getMyInfo(String userName,
                             Model model){

        UserDTO userDTO = userService.findUserDTO(userName);

        // 테스트 용
        userMissionService.getListUserMission(userName);



        log.error("userDTO : " + userDTO);
        model.addAttribute("userDTO", userDTO);

        return "/myPage/myInfo";
    }
    
}
