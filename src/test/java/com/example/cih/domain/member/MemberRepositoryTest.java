package com.example.cih.domain.member;

import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void insertMembers() {
        IntStream.rangeClosed(1, 10).forEach(i -> {

            Member member = Member.builder()
                    .memberId("member" + i)
                    .memberPw(passwordEncoder.encode("1111"))
                    .email("email" + i + "@naver.com")
                    .build();
            member.addRole(MemberRole.USER);

            if (i >= 90) {
                member.addRole(MemberRole.ADMIN);
            }
            memberRepository.save(member);

        });
    }

    @Test
    public void testRead(){
        Optional<Member> member100 = memberRepository.getWithRoles("member10");
        Member member = member100.orElseThrow();


        log.info(member);
        log.info(member.getRoleSet());

        member.getRoleSet().forEach(log::error);


    }

}