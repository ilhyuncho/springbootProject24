package com.example.cih.service.member;

import com.example.cih.dto.member.MemberJoinDTO;

public interface MemberService {

    static class MemberIdExistException extends  Exception{

    }
    void register(MemberJoinDTO memberJoinDTO) throws MemberIdExistException;
}
