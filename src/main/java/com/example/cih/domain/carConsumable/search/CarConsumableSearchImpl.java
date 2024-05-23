package com.example.cih.domain.carConsumable.search;

import com.example.cih.domain.carConsumable.CarConsumable;
import com.example.cih.domain.carConsumable.QCarConsumable;
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

import java.time.LocalDate;
import java.util.List;

@Log4j2
public class CarConsumableSearchImpl extends QuerydslRepositorySupport implements CarConsumableSearch {
    public CarConsumableSearchImpl() {
        super(CarConsumable.class);
    }


    @Override
    public Page<CarConsumable> statisticsConsume(String[] types, String keyword) {

        QCarConsumable carConsumable = QCarConsumable.carConsumable;
        JPQLQuery<CarConsumable> query = from(carConsumable);

        if ((types != null && types.length > 0) && keyword != null) {
            BooleanBuilder booleanBuilder = new BooleanBuilder();
            for (String type : types) {
                switch (type) {
                    case "m":
                        booleanBuilder.or(carConsumable.refConsumableId.eq(1L));
                        break;
                }
            }
            query.where(booleanBuilder);
        }
        query.where(carConsumable.replaceDate.between(LocalDate.of(2024,4,1)
                                                     ,LocalDate.of(2024,6,1)));


        //this.getQuerydsl().applyPagination(pageable, query);

        List<CarConsumable> list = query.fetch();
        long count = query.fetchCount();

        for (CarConsumable car : list) {
            log.error(car.getConsumableId() + ", " + car.getAccumKm() + ", " + car.getReplacePrice());
        }

        return null;
    }
}
