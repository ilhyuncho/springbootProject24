package com.example.cih.domain.sellingCar.search;

import com.example.cih.domain.sellingCar.SellingCar;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SellingCarSearch {
    Page<SellingCar> searchAll(String[] types, String keyword, String[] typeExts, Pageable pageable);
}
