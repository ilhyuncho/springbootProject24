package com.example.cih.controller.myPage;


import com.example.cih.domain.user.User;
import com.example.cih.dto.PageRequestDTO;
import com.example.cih.dto.user.UserCreditDTO;
import com.example.cih.service.user.UserCreditService;
import com.example.cih.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/userCredit")
@RequiredArgsConstructor
@Log4j2
//@PreAuthorize("hasRole('USER')")
public class UserCreditController {
    private final UserService userService;
    private final UserCreditService userCreditService;


    @GetMapping("/info")
    public String info(PageRequestDTO pageRequestDTO, String memberId, Model model){

        User user = userService.findUser(memberId);

        UserCreditDTO userCreditDTO = userCreditService.readCreditInfo(user);

        model.addAttribute("responseDTO", userCreditDTO);

        return "/userCredit/creditInfo";
    }

    @GetMapping("/register")
    public String myCredit(PageRequestDTO pageRequestDTO, Long carId, Model model){

        log.error("register!!!");
        return "/userCredit/creditRegister";
    }
    @PostMapping(value="/register")
    public String myCredit(@Valid UserCreditDTO userCreditDTO,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes,
                           Principal principal     // 임시로 다른 인증 정보 받아오는 법 확인해 보자 ( @AuthenticationPrincipal )
                           ) {
        log.error("userCreditDTO : " + userCreditDTO);

        if(bindingResult.hasErrors()) {
            // throw new BindException(bindingResult);
            // 바로 에러 처리 하지 말고.. 다시 입력창으로 redirect 시키고... 팝업 노출
            log.error("myCredit- bindingResult.hasErrors(!!!");
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/userCredit/register";
        }

        User user = userService.findUser(principal.getName()); // 고객 정보 get

        Long bno = userCreditService.register(user, userCreditDTO);

        return "redirect:/userCredit/info?userName=" + principal.getName();
    }



}
