package com.example.cih.common.handler;

import com.example.cih.domain.user.UserActionType;
import com.example.cih.service.user.UserMissionService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Log4j2
@AllArgsConstructor
@Component      // 스테레오 타입 애너테이션
public class LoginSuccessHandler implements AuthenticationSuccessHandler{

    private final UserMissionService userMissionService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        log.error("onAuthenticationSuccess~~~ : " + authentication.getName());

        userMissionService.insertUserMission(authentication.getName(), UserActionType.ACTION_LOGIN);

        // 메인 페이지에서 출력
        HttpSession session = request.getSession();
        session.setAttribute("greeting", authentication.getName() + "님 반갑습니다.");
        response.sendRedirect("/");
    }
}
