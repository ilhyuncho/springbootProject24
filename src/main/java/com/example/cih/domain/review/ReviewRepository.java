package com.example.cih.domain.review;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ReviewRepository extends JpaRepository<Review, String> {

    Optional<Review> findByOrderItemIdAndReviewer(Long orderItemId, String memberId);

}
