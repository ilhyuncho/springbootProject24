package com.example.cih.common.jwt.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


//@Component        // 주석 풀면 현재 사이트 접근 불가
@RequiredArgsConstructor
@Log4j2
public class JWTCheckFilter extends OncePerRequestFilter {  // OncePerRequestFilter ->
                                                            // 모든 요청에 대해서 동작하는 필터를 작성할 때 사용
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        
        // JWTCheckFilter가 동작하지 않아야 하는 경로를 지정하기 위해서 사용

        if(request.getServletPath().startsWith("/swagger-ui/")){    // 동작하지 않음
            return true;
        }

        //return super.shouldNotFilter(request);
        return false;   // 모든 경로를 필터링 함
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // Access Token을 꺼내서 검증 후 문제가 없는 경우 컨트롤러 혹은 다음 필터들이 동작하도록 구성
        // 만일 Access Token에 문제가 있는 경우에는 JwtException이 발생 됨

        log.error("JWTCheckFilter doFilterInternal()~~~~~~~~~~~~");
        log.error("requestURI: " + request.getRequestURI());

        String headerStr = request.getHeader("Authorization");

        log.error("headerStr: " + headerStr);

        // Access Token이 없는 경우
        if(headerStr == null || !headerStr.startsWith("Bearer ")){
            handleException(response, new Exception("ACCESS TOKEN NOT FOUND"));
            return;
        }
    }

    private void handleException(HttpServletResponse response, Exception e) throws IOException{
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        response.getWriter().println("{\"error\":\"" + e.getMessage() + "\"}");
    }

}
