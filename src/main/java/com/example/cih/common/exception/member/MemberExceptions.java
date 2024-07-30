package com.example.cih.common.exception.member;

import com.example.cih.domain.member.Member;

public enum MemberExceptions {
    NOT_FOUND("해당 계정을 찾을 수 없습니다", 404),
    DUPLICATE("이미 등록된 계정입니다", 409),
    INVALID("계정 정보가 잘못 되었습니다", 440),
    PASSWORD_NOT_SAME("비밀번호가 일치하지 않습니다", 441),
    BAD_CREDENTIALS("사용자 인증 정보 확인 실패", 401);

    private final MemberTaskException memberTaskException;

    MemberExceptions(String msg, int code){
        memberTaskException = new MemberTaskException(msg, code);
    }

    public MemberTaskException get(){
        return memberTaskException;
    }

    public void get(Member member) {
    }
}
