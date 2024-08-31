package com.example.cih.domain.review;

import com.example.cih.domain.shop.ShopItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface ReviewRepository extends JpaRepository<Review, String> {

    Optional<Review> findByOrderItemIdAndReviewer(Long orderItemId, String memberId);

    Page<Review> findByShopItem(ShopItem shopItem, Pageable pageable);

    Optional<Review> findByReviewId(Long reviewId);

    // 상품 평점 평균 조회
    @Query("select round(avg(coalesce(r.score,0)),1)" +
            " from Review r" +
            " where r.shopItem.shopItemId = :shopItemId ")
    Float getReviewAvgScore(@Param("shopItemId") Long shopItemId);


}
