package com.example.cih.dto.notification;

import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
//@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class NotificationRegDTO {
    private String name;
    private String title;
    private String message;
    private String eventStartTime;
    private String eventEndTime;
    //private String expiredDate;
    private String target;
    private Boolean isUse;
    private Boolean isPopup;

    private List<String> fileNames= new ArrayList<>();

}
