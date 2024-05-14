package com.example.cih.domain.buyingCar.search;

import com.example.cih.domain.buyingCar.BuyingCar;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BuyingCarSearch {
    Page<BuyingCar> searchTest(Pageable pageable);
}
