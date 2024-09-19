package com.example.cih.controller.myPage;


import com.example.cih.domain.user.User;
import com.example.cih.service.user.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/alarm")
@RequiredArgsConstructor
@Log4j2
public class AlarmController {
    private final UserService userService;

    @ApiOperation(value = "[메인화면]에서 알림 아이콘 클릭 ", notes = "고객 접근")
    @GetMapping("/get")
    public String getNews(Model model, Principal principal){

        User user = userService.findUser(principal.getName());

        return "/myPage/alarmInfo";
    }

}
