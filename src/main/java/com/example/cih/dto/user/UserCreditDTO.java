package com.example.cih.dto.user;


import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserCreditDTO {
    private Long userCreditID;
    private Long userId;
    private String bankName;
    private String bankAccount;

}
