package com.example.cih.config.MVCWeb;

import com.example.cih.common.Interceptor.LoggerInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

@Configuration
public class WebServerConfig  implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        // 1.board 경로에 대해 interceptor 발생
        registry.addInterceptor(new LoggerInterceptor())
                //.addPathPatterns("/buyingCar/*")
                .addPathPatterns("/**")
                .excludePathPatterns("/css/**", "/images/**", "/js/**", "/view/**");    // 이미지 파일 요청 추가

//        // 2.Locale interceptor 설정
//        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
//        // 클라이언트가 웹 서버에 리소스를 요청할때 Accept-Language헤더가 아닌 파라미터로 Locale값을 변경하고 싶다면
//        // localeChangeInterceptor 를 사용
//        // 사용자가 GET /Hotels?locale=ko로 요청하면 locale 파리미터 값 'ko'를 사용하여 Locale 객체를 생성하고
//        //애플리케이션 내부에서 쓸수 있다.
//        localeChangeInterceptor.setParamName("locale");
//        registry.addInterceptor(localeChangeInterceptor).excludePathPatterns("/favicon.ico");
    }
}
