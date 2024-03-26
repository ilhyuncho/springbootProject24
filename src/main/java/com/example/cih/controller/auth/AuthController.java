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

    }
    @GetMapping("/register")
    public void register(){

    }

}
