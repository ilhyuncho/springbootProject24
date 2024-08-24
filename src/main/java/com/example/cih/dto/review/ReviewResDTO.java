package com.example.cih.dto.review;

import com.example.cih.dto.ImageListDTO;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReviewResDTO extends ImageListDTO {
    private String reviewer;
    private String reviewText;
    private int score;
    private LocalDate regDate;
}
