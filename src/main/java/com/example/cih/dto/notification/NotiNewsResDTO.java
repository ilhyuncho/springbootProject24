package com.example.cih.dto.notification;


import com.example.cih.dto.ImageDTO;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class NotiNewsResDTO extends NotiResDTO {
    private String target;
}
