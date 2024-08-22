package com.example.cih.dto.review;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReviewResDTO {
    private String reviewer;
    private String reviewText;
    private int score;
}
