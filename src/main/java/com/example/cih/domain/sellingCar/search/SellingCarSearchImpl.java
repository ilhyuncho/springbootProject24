package com.example.cih.domain.sellingCar.search;

import com.example.cih.domain.buyingCar.BuyingCar;
import com.example.cih.domain.buyingCar.QBuyingCar;
import com.example.cih.domain.buyingCar.search.BuyingCarSearch;
import com.example.cih.domain.sellingCar.QSellingCar;
import com.example.cih.domain.sellingCar.SellingCar;
import com.example.cih.domain.sellingCar.SellingCarStatus;
import com.example.cih.dto.sellingCar.SellingCarViewDTO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

@Log4j2
public class SellingCarSearchImpl extends QuerydslRepositorySupport implements SellingCarSearch {
    public SellingCarSearchImpl() {
        super(SellingCar.class);
    }


    @Override
    public Page<SellingCar> searchAll(String[] types, String keyword, Pageable pageable) {

        QSellingCar sellingCar = QSellingCar.sellingCar;
        JPQLQuery<SellingCar> query = from(sellingCar);

        if ((types != null && types.length > 0) && keyword != null) {
            BooleanBuilder booleanBuilder = new BooleanBuilder();
            for (String type : types) {
                switch (type) {
                    case "m":
                        booleanBuilder.or(sellingCar.car.carModel.contains(keyword));
                        break;
                    case "y":
                        log.error("keyword" + keyword);
                        booleanBuilder.or(sellingCar.car.carYears.eq(Integer.valueOf(keyword)));
                        break;
                }
            }
            query.where(booleanBuilder);
        }
        query.where(sellingCar.sellingCarStatus.eq(SellingCarStatus.PROCESSING));

        this.getQuerydsl().applyPagination(pageable, query);
        List<SellingCar> list = query.fetch();
        long count = query.fetchCount();

//        for (SellingCar car : list) {
//            log.error(car.getSellingCarId() + "," + car.getCar().getCarNumber() + "," + car.getCar().getCarModel());
//        }

        return new PageImpl<>(list, pageable, count);
    }
}
