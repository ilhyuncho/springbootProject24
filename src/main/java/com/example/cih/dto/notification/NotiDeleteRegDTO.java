package com.example.cih.dto.notification;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

//@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class NotiDeleteRegDTO {
    //private String name;
    private List<String> fileNames= new ArrayList<>();
}
