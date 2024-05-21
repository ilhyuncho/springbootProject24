package com.example.cih.controller.myPage;


import com.example.cih.common.fileHandler.FileHandler;
import com.example.cih.domain.user.User;
import com.example.cih.service.car.UserCarService;
import com.example.cih.service.user.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/history")
@RequiredArgsConstructor
@Log4j2
@PreAuthorize("hasRole('USER')")
public class HistoryController {

    private final UserCarService userCarService;
    private final UserService userService;

    @ApiOperation(value = "내차 기록 화면", notes = "")
    @GetMapping
    public String get(@ModelAttribute("carId") Long carId,
                      String userName, Model model){

        User user = userService.findUser(userName);

        log.error("history-get : " + carId);

        return "/myPage/carHistory";
    }

}
