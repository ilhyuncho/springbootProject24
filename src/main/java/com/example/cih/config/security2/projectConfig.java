package com.example.cih.config.security2;



import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

//@Configuration
//@RequiredArgsConstructor
//public class projectConfig  extends WebSecurityConfigurerAdapter {
//
//    private final PasswordEncoder passwordEncoder;
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//
//        var userDetailsService = new InMemoryUserDetailsManager();
//
//        var user = User.withUsername("cih")
////                .password("1111")
//                .password(passwordEncoder.encode("1111"))
//                .authorities("ROLE_USER")
//                .build();
//        userDetailsService.createUser(user);
//
//        auth.userDetailsService(userDetailsService);
//
//      //  super.configure(auth);
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        //http.httpBasic();
//        http.formLogin().loginPage("/auth/login");
//
//
//        http.authorizeRequests()
//                .antMatchers("/",
//                        "/auth/login","/loginForm","/joinForm", // 수정한 부분
//                        "/project/list").permitAll()
//                .anyRequest().authenticated();
//
//        //super.configure(http);
//    }
//
//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**");
//    }
//
////    @Bean
////    public UserDetailsService userDetailService(){
////        var userDetailsService = new InMemoryUserDetailsManager();
////
////
////        var user = User.withUsername("cih")
//////                .password("1111")
////                .password(passwordEncoder.encode("1111"))
////                .authorities("ROLE_USER")
////                .build();
////        userDetailsService.createUser(user);
////
////        return userDetailsService;
////    }
//}
