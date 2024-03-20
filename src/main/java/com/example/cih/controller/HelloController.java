package com.example.cih.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("/hello")
    public void hello(Model model){


        // hello.html 으로 이동
        model.addAttribute("msg", "HELLO CIH!!");
    }
}