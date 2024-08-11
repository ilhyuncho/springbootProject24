package com.example.cih.dto.member;

import lombok.Builder;
import lombok.Data;

//@Builder  // @Builder 를 추가하면.. html에서 값 전달시  isDel, isSocial 을 반드시 보내야 함..
@Data
public class MemberJoinDTO {

    private String memberId;
    private String memberPw;
    private String memberName;
    private String email;
    private boolean isDel;
    private boolean isSocial;
}
