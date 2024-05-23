package com.example.cih.domain.carConsumable.search;

import com.example.cih.domain.carConsumable.CarConsumable;
import com.example.cih.domain.sellingCar.SellingCar;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CarConsumableSearch {
    Page<CarConsumable> statisticsConsume(String[] types, String keyword);
}
