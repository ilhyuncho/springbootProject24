package com.example.cih.dto.notification;


import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class NotiEventResDTO extends NotiResDTO {

    // DTO 정리 필요
    private LocalDateTime eventStartTime;
    private LocalDateTime eventEndTime;

    private LocalDate eventStartDate;
    private LocalDate eventEndDate;

    private String eventSelectType;
    private Integer eventValue;

}
