package com.example.cih.dto.notification;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class NotificationResDTO {
    private Long notiId;
    private String notiMessage;
    private String notiTarget;

}
