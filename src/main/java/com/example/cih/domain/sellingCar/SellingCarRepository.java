package com.example.cih.domain.sellingCar;

import com.example.cih.domain.sellingCar.search.SellingCarSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellingCarRepository extends JpaRepository<SellingCar, Long>, SellingCarSearch {

    Page<SellingCar> findAllBySellingCarStatus(SellingCarStatus sellingCarStatus, Pageable pageable);
}

