package com.example.cih.common.Interceptor;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Log4j2
public class LoggerInterceptor implements HandlerInterceptor {

    //Controller 메서드 호출 전
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Object handler -> 컨트롤러 클래스의 핸들러 메서드를 참조하는 HandlerMethod 객체이다.
        log.error("==================== BEGIN ====================");
        log.error("Request URI ==> ({}), {}", request.getMethod(), request.getRequestURI());

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    //Controller 메서드 호출 후
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.error("==================== END ======================");
        log.error("");
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    //View 처리까지 완료 후 : 뷰가 실행 완료 된 후 최종적으로 DispatcherServlet이 사용자에게 응답하기 전 실행
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
