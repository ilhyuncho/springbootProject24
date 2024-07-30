package com.example.cih.service.member;

import com.example.cih.common.exception.member.MemberExceptions;
import com.example.cih.common.jwt.util.MemberDTO;
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
    public void registerMember(MemberJoinDTO memberJoinDTO){

        boolean isExisted = memberRepository.existsById(memberJoinDTO.getMemberId());
        if(isExisted){
            //throw new MemberIdExistException();
            throw MemberExceptions.DUPLICATE.get();
        }

        // Member 전용 예외 처리 방법
//        memberRepository.findById(memberJoinDTO.getMemberId())
//                .orElseThrow(MemberExceptions.DUPLICATE::get);

        Member member = modelMapper.map(memberJoinDTO, Member.class);
        member.changePassword(passwordEncoder.encode(member.getMemberPw()));
        member.addRole(MemberRole.USER);

        memberRepository.save(member);
    }

    @Override
    public MemberDTO getMember(String memberId, String memberPw) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(MemberExceptions.BAD_CREDENTIALS::get);

        if(!passwordEncoder.matches(memberPw, member.getMemberPw())){
            throw MemberExceptions.BAD_CREDENTIALS.get();
        }

        return new MemberDTO(member.getMemberId(), member.getMemberPw(), member.getEmail());
    }


}
