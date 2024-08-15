package com.example.cih.dto.user;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserPointHistoryReqDTO {
    private String fromDay;
    private String toDay;
}
