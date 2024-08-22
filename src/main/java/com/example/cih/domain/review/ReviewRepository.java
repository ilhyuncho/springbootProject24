package com.example.cih.domain.review;

import com.example.cih.domain.shop.ShopItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ReviewRepository extends JpaRepository<Review, String> {

    Optional<Review> findByOrderItemIdAndReviewer(Long orderItemId, String memberId);

    Page<Review> findByShopItem(ShopItem shopItem, Pageable pageable);
}
