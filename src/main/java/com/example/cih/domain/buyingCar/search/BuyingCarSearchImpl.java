package com.example.cih.domain.buyingCar.search;

import com.example.cih.domain.buyingCar.BuyingCar;
import com.example.cih.domain.buyingCar.QBuyingCar;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Log4j2
public class BuyingCarSearchImpl extends QuerydslRepositorySupport implements BuyingCarSearch {
    public BuyingCarSearchImpl() {
        super(BuyingCar.class);
    }

    @Override
    public Page<BuyingCar> searchTest(Pageable pageable) {

        QBuyingCar buyingCar = QBuyingCar.buyingCar;

        JPQLQuery<BuyingCar> query = from(buyingCar);

        // where 조건에 and와 r이 섞여 있을때
//        BooleanBuilder booleanBuilder = new BooleanBuilder();
//        booleanBuilder.or(buyingCar.buyingCar.buyingCarId.eq(1L));
//        booleanBuilder.or(buyingCar.buyingCar.buyingCarId.eq(1L));
//        query.where(booleanBuilder);


        query.where(buyingCar.user.userId.eq(1L));

//        int year = 2020, month = 5, dayOfMonth = 16, hour = 6, minute = 24, second = 44;
//        LocalDateTime of = LocalDateTime.of(year, month, dayOfMonth, hour, minute, second);
//        query.where(buyingCar.registerDate.gt(of));

        //paging
        this.getQuerydsl().applyPagination(pageable, query);    // MariaDB가 페이징 처리에 사용하는 limit을 적용

        List<BuyingCar> buyingCarList = query.fetch();
        long count = query.fetchCount();

        for (BuyingCar car : buyingCarList) {
            log.error(car.getBuyingCarId() + "," + car.getProposalPrice());
        }
        log.error("count:" + count);

        return null;
    }

}
