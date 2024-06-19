package com.example.cih.dto.user;


import com.example.cih.domain.user.UserGradeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long userID;
    private String userName;
    private String address;
    private String billingAddress;

    private UserGradeType mGrade;     // 등급
    private Integer mPoint;     // 획득 포인트
}
