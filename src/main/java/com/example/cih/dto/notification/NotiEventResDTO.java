package com.example.cih.dto.notification;


import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class NotiEventResDTO extends NotiResDTO {

    private LocalDateTime eventStartTime;
    private LocalDateTime eventEndTime;

}
