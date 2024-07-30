package com.example.cih.common.jwt.util;

import lombok.*;

import java.util.HashMap;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class MemberDTO {

    private String memberId;
    private String memberPw;
    private String email;

    // JWT문자열의 내용을 만들때 사용할 데이터
    public Map<String, Object> getDataMap(){
        Map<String, Object> map = new HashMap<>();
        map.put("memberId", memberId);
        map.put("email", email);
        return map;
    }
}
