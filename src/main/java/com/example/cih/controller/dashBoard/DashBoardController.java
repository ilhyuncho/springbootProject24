package com.example.cih.controller.dashBoard;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dashBoard")
@RequiredArgsConstructor
@Log4j2
public class DashBoardController {

    @GetMapping("/list")
    public void list(){
        log.error("DashBoard list()~~~~~~~~~~");
    }
    @GetMapping("/charts")
    public void charts(){
        log.error("DashBoard charts()~~~~~~~~~~");
    }

}
