package com.example.cih.service.review;


import com.example.cih.common.exception.ItemNotFoundException;
import com.example.cih.domain.review.Review;
import com.example.cih.domain.review.ReviewRepository;
import com.example.cih.domain.shop.ShopItem;
import com.example.cih.domain.shop.ShopItemRepository;
import com.example.cih.domain.user.User;
import com.example.cih.dto.review.ReviewWriteReqDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;


@Log4j2
@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ShopItemRepository shopItemRepository;

    @Override
    public void writeReview(User user, ReviewWriteReqDTO reviewWriteReqDTO) {

        Long shopItemId = reviewWriteReqDTO.getShopItemId();

        ShopItem shopItem = shopItemRepository.findById(shopItemId)
                .orElseThrow(() -> new ItemNotFoundException("해당 상품이 존재하지않습니다"));

        Review review = Review.builder()
                .reviewer(user.getMemberId())
                .reviewText(reviewWriteReqDTO.getReviewText())
                .score(5)
                .shopItem(shopItem)
                .build();

        reviewRepository.save(review);
    }
}
