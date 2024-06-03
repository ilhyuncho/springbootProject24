package com.example.cih.dto.notification;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class NotificationResDTO {
    private Long notiId;
    private String name;
    private String title;
    private String message;
    private LocalDate expiredDate;

}
