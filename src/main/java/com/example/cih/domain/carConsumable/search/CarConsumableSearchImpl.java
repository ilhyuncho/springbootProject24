package com.example.cih.domain.carConsumable.search;

import com.example.cih.domain.carConsumable.CarConsumable;
import com.example.cih.domain.carConsumable.QCarConsumable;
import com.example.cih.dto.statistics.StatisticsReqDTO;
import com.example.cih.dto.statistics.StatisticsResDTO;
import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.time.LocalDate;
import java.util.List;

@Log4j2
public class CarConsumableSearchImpl extends QuerydslRepositorySupport implements CarConsumableSearch {
    public CarConsumableSearchImpl() {
        super(CarConsumable.class);
    }


    @Override
    public List<StatisticsResDTO> statisticsConsume(StatisticsReqDTO statisticsReqDTO) {

        LocalDate selectDate = LocalDate.of(statisticsReqDTO.getSelectYear(), 1, 1);

        QCarConsumable carConsumable = QCarConsumable.carConsumable;

        //        DateTemplate<LocalDate> formattedDate = Expressions.dateTemplate(
//                LocalDate.class
//                ,"DATE_FORMAT({0}, {1})"
//                , carConsumable.replaceDate
//                , "%Y-%m-%d");

        StringTemplate formattedDateYearMonth = Expressions.stringTemplate(
                "DATE_FORMAT({0}, {1})"
                , carConsumable.replaceDate
                , ConstantImpl.create("%Y-%m"));

        StringTemplate formattedDateOnlyMonth = Expressions.stringTemplate(
                "DATE_FORMAT({0}, {1})"
                , carConsumable.replaceDate
                , ConstantImpl.create("%m"));

        JPQLQuery<CarConsumable> query = from(carConsumable);
        query.groupBy(formattedDateYearMonth);

        query.where(carConsumable.replaceDate.year().eq(selectDate.getYear()));

        JPQLQuery<StatisticsResDTO> dtoQuery = query.select(Projections.bean(StatisticsResDTO.class
                ,formattedDateOnlyMonth.as("replaceDate")
                ,carConsumable.replacePrice.sum().as("replacePrice")
        ));

        List<StatisticsResDTO> list = dtoQuery.fetch();
        long count = query.fetchCount();

        for (StatisticsResDTO dto : list) {
            log.error(dto.getReplaceDate() + ", " + dto.getReplacePrice());
        }

        return list;
    }
}
