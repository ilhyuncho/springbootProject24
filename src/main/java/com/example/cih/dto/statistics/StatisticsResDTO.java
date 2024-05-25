package com.example.cih.dto.statistics;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StatisticsResDTO {

    private String eventDate;
    private int eventValue;

}
