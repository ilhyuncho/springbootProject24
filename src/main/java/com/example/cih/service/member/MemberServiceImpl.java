package com.example.cih.service.member;

import com.example.cih.domain.member.Member;
import com.example.cih.domain.member.MemberRepository;
import com.example.cih.domain.member.MemberRole;
import com.example.cih.dto.member.MemberJoinDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    @Override
    public void registerMember(MemberJoinDTO memberJoinDTO) throws MemberIdExistException {

        boolean isExisted = memberRepository.existsById(memberJoinDTO.getMemberId());

        if(isExisted){
            throw new MemberIdExistException();
        }

        Member member = modelMapper.map(memberJoinDTO, Member.class);
        member.changePassword(passwordEncoder.encode(member.getMemberPw()));
        member.addRole(MemberRole.USER);

        memberRepository.save(member);
    }



}
