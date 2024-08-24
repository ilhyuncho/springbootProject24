package com.example.cih.dto.review;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReviewWriteReqDTO {
    private Long orderId;
    private Long orderItemId;
    private Long shopItemId;
    private String reviewText;
    private int score;              // 만족도 별 갯수

    @Builder.Default
    private List<String> fileNames= new ArrayList<>();

    String mainImageFileName;

}
