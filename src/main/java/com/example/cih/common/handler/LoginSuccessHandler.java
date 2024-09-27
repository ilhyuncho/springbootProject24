package com.example.cih.common.handler;

import com.example.cih.common.message.MessageCode;
import com.example.cih.common.message.MessageHandler;
import com.example.cih.domain.user.UserActionType;
import com.example.cih.service.user.UserPointHistoryService;
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
import java.util.ArrayList;
import java.util.List;

@Log4j2
@AllArgsConstructor
@Component      // 스테레오 타입 애너테이션
public class LoginSuccessHandler implements AuthenticationSuccessHandler{

    private final UserPointHistoryService userPointHistoryService;

    private final MessageHandler messageHandler;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        log.error("onAuthenticationSuccess~~~ : " + authentication.getName());

        // 포인트 획득 처리
        userPointHistoryService.gainUserPoint(authentication.getName(), UserActionType.ACTION_LOGIN);

        // 메인 페이지에서 출력
        HttpSession session = request.getSession();

        // Locale 메시지 정보 가져오기
        List<String> listArgs = new ArrayList<>();
        listArgs.add(authentication.getName());
        String message = messageHandler.getMessage(MessageCode.WELCOME_GREETING, listArgs);

        session.setAttribute("greeting", message);
        response.sendRedirect("/");
    }
}
