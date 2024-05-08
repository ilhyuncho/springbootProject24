package com.example.cih.domain.sellingCar;

import com.example.cih.domain.car.Projection;
import com.example.cih.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BuyRequestRepository extends JpaRepository<BuyRequest, Long> {
    List<BuyRequest> findBySellingCar(SellingCar sellingCar);
}

