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
    private String eventStartDate;
    private String eventEndDate;

    private String target;
    private Boolean isUse;
    private Boolean isPopup;

    private Integer eventSelectType;
    private Integer eventValue;

    private List<String> fileNames= new ArrayList<>();

}
