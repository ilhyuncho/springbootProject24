package com.example.cih.config.security3;

import com.example.cih.config.security.CustomUserDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

//@RequiredArgsConstructor
//@EnableWebSecurity
//@Configuration
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    private final CustomUserDetailsService customUserDetailsService;
//
//    @Override
//    public void configure(WebSecurity web) {
//        web.ignoring().antMatchers("/actuator/**", "/css/**", "/js/**", "/assets/**", "item/**");
//    }
//
//    @Override
//    protected void configure(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity
//                .csrf().disable()
//                .rememberMe()
//                .rememberMeParameter("remember-me")
////                토큰 유효기간 1달
//                .tokenValiditySeconds(86400 * 30)
////                .alwaysRemember(true)
//                .userDetailsService(customUserDetailsService)
//                .and()
//                .authorizeRequests()
//                .antMatchers("/demo/**", "/random-user-generator", "/api/v1/item/option/add", "/api/v1/page/**", "/api/v1/item/zzim", "/api/v1/cart/total", "/api/v1/auth/forgot-password").permitAll()
//                .antMatchers("/auth/new-password", "/auth/reset**", "/auth/login**", "/auth/signup-done", "/auth/seller-signup", "/auth/signup", "/auth/forgot-password").permitAll()
//                .antMatchers("/item/details/null", "/seller/null", "/admin/null", "/auth/null", "/null").permitAll()
//                .antMatchers("/search**",  "/", "/item/**","/item/details/**").permitAll()
//
//                .antMatchers("/me/**", "/seller/start", "/seller/agree").hasRole("USER")
//                .antMatchers("/seller/**").hasRole("SELLER")
//                .antMatchers( "/swagger-ui.html**", "/admin/**").hasRole("ADMIN")
//                .anyRequest().authenticated()
//                .and()
//                .formLogin()
//                .usernameParameter("username")
//                .passwordParameter("password")
//                .loginPage("/auth/login")
//                .permitAll()
//
//                .permitAll()
//                .and()
//                .logout()
//                .logoutSuccessUrl("/auth/login")
//                .deleteCookies("JSESSIONID", "remember-me")
//                .invalidateHttpSession(true)
//        ;
//    }
//
//    @Bean
//    public ObjectMapper objectMapper() {
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.registerModule(new JavaTimeModule());
//        return objectMapper;
//    }
//
//    @Bean
//    @Override
//    public AuthenticationManager authenticationManager() throws Exception {
//        return super.authenticationManager();
//    }
//
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return NoOpPasswordEncoder.getInstance();
//    }
//
//    //권한별 계층 구조 설정
//
//
//    //권한 계층 구조 등록
//
//}
