package com.example.cih.service.review;


import com.example.cih.common.exception.ItemNotFoundException;
import com.example.cih.domain.review.Review;
import com.example.cih.domain.review.ReviewRepository;
import com.example.cih.domain.shop.ShopItem;
import com.example.cih.domain.shop.ShopItemRepository;
import com.example.cih.domain.user.User;
import com.example.cih.dto.PageRequestDTO;
import com.example.cih.dto.review.ReviewListResDTO;
import com.example.cih.dto.review.ReviewResDTO;
import com.example.cih.dto.review.ReviewWriteReqDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;


@Log4j2
@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ShopItemRepository shopItemRepository;

    @Override
    public ReviewListResDTO<ReviewResDTO> getListReview(PageRequestDTO pageRequestDTO, Long shopItemId) {

        ShopItem shopItem = shopItemRepository.findById(shopItemId)
                .orElseThrow(() -> new ItemNotFoundException("해당 상품이 존재하지않습니다"));

        Pageable pageable = pageRequestDTO.getPageable("reviewId");

        // 리뷰 리스트 get
        Page<Review> result = reviewRepository.findByShopItem(shopItem, pageable);

        List<ReviewResDTO> listReviewDTO = result.getContent().stream()
                .map(ReviewServiceImpl::entityToDTO)
                .collect(Collectors.toList());


        // 리뷰 평균 별 점수 get
        Float reviewAvgScore = reviewRepository.getReviewAvgScore(shopItemId);

        return ReviewListResDTO.<ReviewResDTO>withSuper()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(listReviewDTO)
                .total((int)result.getTotalElements()) // 수정 해야 함!!!
                .reviewAvgScore(reviewAvgScore == null ? 0 : reviewAvgScore)
                .build();
    }

    @Override
    public ReviewResDTO getReview(Long reviewId) {

        Review review = reviewRepository.findByReviewId(reviewId)
                .orElseThrow(() -> new NoSuchElementException("해당 리뷰 정보가 존재하지않습니다"));

        return entityToDTO(review);
    }

    @Override
    public void writeReview(User user, ReviewWriteReqDTO reviewWriteReqDTO) {

        Long shopItemId = reviewWriteReqDTO.getShopItemId();

        ShopItem shopItem = shopItemRepository.findById(shopItemId)
                .orElseThrow(() -> new ItemNotFoundException("해당 상품이 존재하지않습니다"));

        Review review = Review.builder()
                .reviewer(user.getMemberId())
                .reviewText(reviewWriteReqDTO.getReviewText())
                .score(reviewWriteReqDTO.getScore())
                .orderId(reviewWriteReqDTO.getOrderId())
                .orderItemId(reviewWriteReqDTO.getOrderItemId())
                .shopItem(shopItem)
                .build();

        // 리뷰 이미지 set
        if(reviewWriteReqDTO.getFileNames() != null){
            reviewWriteReqDTO.getFileNames().forEach(fileName ->{

                String[] arr = fileName.split("_");
                review.addImage(arr[0], arr[1], arr[1].equals(reviewWriteReqDTO.getMainImageFileName()));
            });
        }

        reviewRepository.save(review);
    }

    private static ReviewResDTO entityToDTO(Review review) {

        ReviewResDTO reviewResDTO = ReviewResDTO.builder()
                .reviewId(review.getReviewId())
                .reviewer(review.getReviewer())
                .reviewText(review.getReviewText())
                .score(review.getScore())
                .regDate(review.getRegDate().toLocalDate())
                .build();

        // 차 이미지 파일 정보 매핑
        review.getReviewImageSet().forEach(reviewImage -> {
            reviewResDTO.addImage(reviewImage.getReviewImageId(), reviewImage.getUuid(), reviewImage.getFileName(),
                    reviewImage.getImageOrder(), reviewImage.getIsMainImage());
        });

        return reviewResDTO;
    }
}
