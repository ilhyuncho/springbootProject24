package com.example.cih.dto.user;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserMissionResDTO {

    private String refMissionName;
    private Integer gainPoint;
    private LocalDate regDate;

}
