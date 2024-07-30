package com.example.cih.dto.user;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserPasswordReqDTO {
    private String currentPassword;
    private String newPassword;
    private String newPasswordConfirm;
}
