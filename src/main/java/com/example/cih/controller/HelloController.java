package com.example.cih.controller;



import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Log4j2
public class HelloController {

    @GetMapping("/hello")
    public void hello(Model model){

        log.info("hello()!!!");
        log.error("error hello()!!!");
        // hello.html 으로 이동
        model.addAttribute("msg", "HELLO CIH!!");
    }
}