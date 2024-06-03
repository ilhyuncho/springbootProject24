package com.example.cih.dto.notification;

import lombok.*;

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
    private String expiredDate;
    private String target;
    private List<String> fileNames= new ArrayList<>();

}
