package com.example.cih.dto.statistics;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StatisticsReqDTO {
    private Long carId;
    private int selectYear;
}
