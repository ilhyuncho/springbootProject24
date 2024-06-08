package com.example.cih.dto.notification;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
//@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class NotiDeleteRegDTO {
    //private String name;
    private List<String> fileNames= new ArrayList<>();
}
