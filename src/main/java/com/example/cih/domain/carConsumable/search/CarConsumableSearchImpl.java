package com.example.cih.domain.carConsumable.search;

import com.example.cih.domain.carConsumable.CarConsumable;
import com.example.cih.domain.sellingCar.QSellingCar;
import com.example.cih.domain.sellingCar.SellingCar;
import com.example.cih.domain.sellingCar.SellingCarStatus;
import com.example.cih.domain.sellingCar.search.SellingCarSearch;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

@Log4j2
public class CarConsumableSearchImpl extends QuerydslRepositorySupport implements CarConsumableSearch {
    public CarConsumableSearchImpl() {
        super(CarConsumable.class);
    }


    @Override
    public Page<CarConsumable> searchAll(String[] types, String keyword, Pageable pageable) {


        return null;
    }
}
