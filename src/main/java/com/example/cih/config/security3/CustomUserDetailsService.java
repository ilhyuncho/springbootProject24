package com.example.cih.config.security3;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

//@Service
//@RequiredArgsConstructor
//public class CustomUserDetailsService implements UserDetailsService {
//
//    //private final UserRepository userRepository;
//
//    //private final PasswordEncoder passwordEncoder;
//
//    @Override
//    public UserDetails loadUserByUsername(final String username) {
//
//        UserDetails userDetails = User.builder().username("user1")
//                .password("1111")
//                .authorities("ROLE_USER")
//                .build();
//        return userDetails;
//    }
//}