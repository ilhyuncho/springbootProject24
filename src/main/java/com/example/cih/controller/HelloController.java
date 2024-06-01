package com.example.cih.controller;



import com.example.cih.common.util.Util;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Controller
@Log4j2
public class HelloController {

    @GetMapping("/")
    public String mainPage(Model model){

        // 임시로
        boolean isItEventTime = LocalDateTime.now()
                .query(time -> {
                    var currTime = LocalTime.from(time);
                    return currTime.getHour() >= 13;
                });

        log.error("isItEventTime : " + isItEventTime);

        model.addAttribute("eventId", "1");

        return "/index";
    }
    @GetMapping("/hello")
    public void hello(Model model){

        // hello.html 으로 이동
        model.addAttribute("msg", "HELLO CIH!!");
    }
}