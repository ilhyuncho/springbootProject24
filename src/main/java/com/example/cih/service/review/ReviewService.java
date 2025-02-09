package com.example.cih.service.review;


import com.example.cih.domain.user.User;
import com.example.cih.dto.PageRequestDTO;
import com.example.cih.dto.review.ReviewListResDTO;
import com.example.cih.dto.review.ReviewResDTO;
import com.example.cih.dto.review.ReviewWriteReqDTO;


public interface ReviewService {
    ReviewListResDTO<ReviewResDTO> getListReview(PageRequestDTO pageRequestDTO, Long shopItemId);
    ReviewResDTO getReview(Long reviewId);
    void writeReview(User user, ReviewWriteReqDTO reviewWriteReqDTO);
}
