package com.example.cih.config.security;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 실제 인증을 처리할때 호출
        log.error("loadUserByUsername : " + username);

        // UserDetails 인터페이스를 구현한 User라는 클래스를 제공
        UserDetails userDetails = User.builder().username("user1")
                //.password("1111")
                .password(passwordEncoder.encode("1111"))
                .authorities("ROLE_USER")
                .build();

        log.error("userDetails userName : " + userDetails.getUsername());
        return userDetails;
    }
}
