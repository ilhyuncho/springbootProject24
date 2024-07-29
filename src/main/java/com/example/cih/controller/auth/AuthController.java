package com.example.cih.controller.auth;

import com.example.cih.common.util.Util;
import com.example.cih.dto.member.MemberJoinDTO;
import com.example.cih.service.member.MemberService;
import com.example.cih.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
@Log4j2

public class AuthController {

    private final MemberService memberService;
    private final UserService userService;

    @GetMapping("/login")
    public void login(String error, String logout){

        log.error("login get................");

        // 로그아웃 호출 URL : http://localhost:8090/logout
        // 으로 redirect : http://localhost:8090/auth/login?logout
        if( logout != null){
            log.error("user logout======================");
        }
    }
    @GetMapping("/register")
    public void register(){

    }

    @PostMapping("/register")
    public String postRegister(MemberJoinDTO memberJoinDTO, RedirectAttributes redirectAttributes){

        // 테스트 용
        if(memberJoinDTO.getMemberId().isEmpty()){
            memberJoinDTO.setMemberId(Util.createRandomName("member"));
        }

        try{
            memberService.registerMember(memberJoinDTO);
            userService.registerUser(memberJoinDTO.getMemberId());
        }
        catch (MemberService.MemberIdExistException ex){
            redirectAttributes.addFlashAttribute("error", "memberId");
            return "redirect:/auth/register";
        }

        redirectAttributes.addFlashAttribute("result", "success");
        return "redirect:/auth/login";
    }

}
