package com.example.cih.dto.notification;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class NotificationRegDTO {
    private String eventName;
    private String eventTitle;

    private List<String> fileNames= new ArrayList<>();

}
