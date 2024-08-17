package com.example.cih.dto.review;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReviewWriteReqDTO {
    private Long orderId;
    private Long shopItemId;
    private String reviewText;

}
