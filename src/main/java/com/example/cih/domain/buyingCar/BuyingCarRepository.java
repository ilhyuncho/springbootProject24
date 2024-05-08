package com.example.cih.domain.buyingCar;

import com.example.cih.domain.sellingCar.SellingCar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BuyingCarRepository extends JpaRepository<BuyingCar, Long> {
    List<BuyingCar> findBySellingCar(SellingCar sellingCar);
}

