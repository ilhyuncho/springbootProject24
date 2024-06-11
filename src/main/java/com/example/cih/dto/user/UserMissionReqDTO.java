package com.example.cih.dto.user;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserMissionReqDTO {

    private String searchMethod;
    private Integer searchPeriod;
    private String fromDay;
    private String toDay;
}
