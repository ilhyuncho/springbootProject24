package com.example.cih.dto.user;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAlarmDTO {
    private Long userAlarmID;
    private String alarmTitle;
    private String alarmContent;
    private LocalDateTime regDate;

}
