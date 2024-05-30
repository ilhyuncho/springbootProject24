package com.example.cih.config.security;


import com.example.cih.domain.member.Member;
import com.example.cih.domain.member.MemberRepository;
import com.example.cih.dto.member.MemberSecurityDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 실제 인증을 처리할때 호출
        log.error("loadUserByUsername!!~~~~~~~~~~~~ : " + username);

        Optional<Member> result = memberRepository.getWithRoles(username);
        if(result.isEmpty()){
            throw new UsernameNotFoundException("username not found...");
        }

        Member member = result.get();

        MemberSecurityDTO memberSecurityDTO = new MemberSecurityDTO(member.getMemberId(),
                                member.getMemberPw(), member.getEmail(), member.isDel(), false,
                                member.getRoleSet().stream().
                                map(memberRole -> new SimpleGrantedAuthority("ROLE_" + memberRole.name())).collect(Collectors.toList()));

        log.error("memberSecurityDTO : " + memberSecurityDTO);
        return memberSecurityDTO;

        // UserDetails 인터페이스를 구현한 User라는 클래스를 제공
//        UserDetails userDetails = User.builder().username("user1")
//                //.password("1111")
//                .password(passwordEncoder.encode("1111"))
//                .authorities("ROLE_USER")
//                .build();

//        log.error("userDetails userName : " + userDetails.getUsername());
//        return userDetails;
    }
}
