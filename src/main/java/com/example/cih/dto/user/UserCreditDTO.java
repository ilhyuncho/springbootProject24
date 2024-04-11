package com.example.cih.dto.user;


import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserCreditDTO {
    private Long userCreditID;
    private Long userId;

    @NotEmpty
    @Size(min = 4, max = 10)
    private String bankName;
    private String bankAccount;

}
