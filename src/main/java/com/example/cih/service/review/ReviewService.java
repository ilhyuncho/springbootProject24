package com.example.cih.service.review;


import com.example.cih.domain.user.User;
import com.example.cih.dto.review.ReviewWriteReqDTO;

public interface ReviewService {

    void writeReview(User user, ReviewWriteReqDTO reviewWriteReqDTO);
}
