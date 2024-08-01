package com.example.cih.common.jwt.util;

import com.example.cih.service.member.MemberService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/token")
@Log4j2
@RequiredArgsConstructor
public class TokenController {

    private final MemberService memberService;
    private final JWTUtil jwtUtil;

    @ApiOperation(value = "JWT 생성 테스트", notes = "swagger UI로 접근")
    @PostMapping("/make")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Map<String, String>> makeToken(@RequestBody MemberDTO memberSecurityDTO){

        log.error("make token.....................");
        log.error("makeToken : " + memberSecurityDTO);

        MemberDTO memberDTO = memberService.getMember(memberSecurityDTO.getMemberId(), memberSecurityDTO.getMemberPw());

        String memberId = memberDTO.getMemberId();
        Map<String , Object> dataMap = memberDTO.getDataMap();

        String accessToken = jwtUtil.createToken(dataMap, 10);

        String refreshToken = jwtUtil.createToken(Map.of("memberId", memberId), 60 * 24 * 7);

        log.error("accessToken: " + accessToken);
        log.error("refreshToken: " + refreshToken);

        return ResponseEntity.ok( Map.of("accessToken", accessToken, "refreshToken", refreshToken));
    }
}
