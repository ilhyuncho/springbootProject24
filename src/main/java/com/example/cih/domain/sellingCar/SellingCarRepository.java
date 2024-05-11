package com.example.cih.domain.sellingCar;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellingCarRepository extends JpaRepository<SellingCar, Long> {

    Page<SellingCar> findAllBySellingCarStatus(SellingCarStatus sellingCarStatus, Pageable pageable);
}

