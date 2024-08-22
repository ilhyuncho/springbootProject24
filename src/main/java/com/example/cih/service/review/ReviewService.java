package com.example.cih.service.review;


import com.example.cih.domain.user.User;
import com.example.cih.dto.PageRequestDTO;
import com.example.cih.dto.PageResponseDTO;
import com.example.cih.dto.review.ReviewResDTO;
import com.example.cih.dto.review.ReviewWriteReqDTO;


public interface ReviewService {
    PageResponseDTO<ReviewResDTO> getListReview(PageRequestDTO pageRequestDTO, Long shopItemId);
    void writeReview(User user, ReviewWriteReqDTO reviewWriteReqDTO);
}
