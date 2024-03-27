package com.example.cih.controller.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
@Log4j2

public class AuthController {

    @GetMapping("/login")
    public void login(String error, String logout){

        log.error("login get................");

        // 로그아웃 호출 URL : http://localhost:8090/auth/login?logout
        if( logout != null){
            log.error("user logout======================");
        }
    }
    @GetMapping("/register")
    public void register(){

    }

}
