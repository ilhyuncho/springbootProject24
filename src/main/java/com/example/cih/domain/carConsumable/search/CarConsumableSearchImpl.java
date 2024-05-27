package com.example.cih.domain.carConsumable.search;

import com.example.cih.domain.car.Car;
import com.example.cih.domain.carConsumable.CarConsumable;
import com.example.cih.domain.carConsumable.ConsumableType;
import com.example.cih.domain.carConsumable.QCarConsumable;
import com.example.cih.dto.statistics.*;
import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.*;

@Log4j2
public class CarConsumableSearchImpl extends QuerydslRepositorySupport implements CarConsumableSearch {
    public CarConsumableSearchImpl() {
        super(CarConsumable.class);
    }


    @Override
    public List<StatisticsResDTO> statisticsConsume(StatisticsReqDTO statisticsReqDTO) {

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

        query.where(carConsumable.replaceDate.year().eq(statisticsReqDTO.getSelectYear()));
        query.where(carConsumable.car.carId.eq(statisticsReqDTO.getCarId()));


        JPQLQuery<StatisticsResDTO> dtoQuery = query.select(Projections.bean(StatisticsResDTO.class
                ,formattedDateOnlyMonth.as("eventDate")
                ,carConsumable.replacePrice.sum().as("eventValue")
        ));

        List<StatisticsResDTO> list = dtoQuery.fetch();
        long count = query.fetchCount();

        for (StatisticsResDTO dto : list) {
            log.error(dto.getEventDate() + ", " + dto.getEventValue());
        }

        return list;
    }

    @Override
    public List<StatisticsResDTO> statisticsFuelAmount(StatisticsReqDTO statisticsReqDTO) {

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

        query.where(carConsumable.replaceDate.year().eq(statisticsReqDTO.getSelectYear()));
        query.where(carConsumable.car.carId.eq(statisticsReqDTO.getCarId()));

        JPQLQuery<StatisticsResDTO> dtoQuery = query.select(Projections.bean(StatisticsResDTO.class
                ,formattedDateOnlyMonth.as("eventDate")
                ,carConsumable.gasLitter.sum().as("eventValue")
        ));

        List<StatisticsResDTO> list = dtoQuery.fetch();
        long count = query.fetchCount();

        for (StatisticsResDTO dto : list) {
            log.error(dto.getEventDate() + ", " + dto.getEventValue());
        }

        return list;
    }

    @Override
    public List<StatisticsResDTO> statisticsDistance(StatisticsReqDTO statisticsReqDTO) {
        QCarConsumable carConsumable = QCarConsumable.carConsumable;

        StringTemplate formattedDateYearMonth = Expressions.stringTemplate(
                "DATE_FORMAT({0}, {1})"
                , carConsumable.replaceDate
                , ConstantImpl.create("%Y-%m"));

        JPQLQuery<CarConsumable> query = from(carConsumable);
        query.groupBy(formattedDateYearMonth);

        query.where(carConsumable.car.carId.eq(statisticsReqDTO.getCarId()));

        JPQLQuery<StatisticsDistanceDTO> dtoQuery = query.select(Projections.bean(StatisticsDistanceDTO.class
                ,carConsumable.replaceDate.as("eventDate")
                ,carConsumable.accumKm.max().as("eventValue")
        ));

        List<StatisticsDistanceDTO> list = dtoQuery.fetch();
        long count = query.fetchCount();

//        for (StatisticsDistanceDTO car : list) {
//            log.error(car.getEventDate() + ", " + car.getEventValue());
//        }

        // 전달 누적 주행 거리를 빼서 각 월을 주행 거리를 계산
        List<StatisticsResDTO> listStatisticsResDTO = getCalcDiffDistance(list, statisticsReqDTO.getSelectYear());

        return listStatisticsResDTO;
    }

    private static List<StatisticsResDTO> getCalcDiffDistance(List<StatisticsDistanceDTO> list, int selectYear) {

        List<StatisticsResDTO> listStatisticsResDTO= new ArrayList<>();
        Map<String, Integer> mapTemp = new HashMap<>();

        for (StatisticsDistanceDTO dto : list) {

            String dateYYYYMM = dto.getEventDate().toString().substring(0,7);
            String dateMM = dto.getEventDate().toString().substring(5, 7);
            String before1MonthYYYYMM = dto.getEventDate().minusMonths(1).toString().substring(0,7);

            mapTemp.put(dateYYYYMM, dto.getEventValue());

            if( mapTemp.containsKey(before1MonthYYYYMM))
            {
                if( dto.getEventDate().getYear() == selectYear) {
                    listStatisticsResDTO.add(StatisticsResDTO.builder()
                            .eventDate(dateMM)
                            .eventValue(dto.getEventValue() - mapTemp.get(before1MonthYYYYMM))
                            .build());
                }
            }
        }

//        for (StatisticsResDTO statisticsResDTO : listStatisticsResDTO) {
//            log.error("======================");
//            log.error(statisticsResDTO.getEventDate() + ", " + statisticsResDTO.getEventValue());
//        }
        return listStatisticsResDTO;
    }

    @Override
    public StatisticsTotalResDTO statisticsTotal(StatisticsReqDTO statisticsReqDTO) {
        QCarConsumable carConsumable = QCarConsumable.carConsumable;

        StringTemplate formattedDateYear = Expressions.stringTemplate(
                "DATE_FORMAT({0}, {1})"
                , carConsumable.replaceDate
                , ConstantImpl.create("%Y"));

        JPQLQuery<CarConsumable> query = from(carConsumable);
        query.groupBy(formattedDateYear, carConsumable.refConsumableId );

        query.where(carConsumable.replaceDate.year().eq(statisticsReqDTO.getSelectYear()));
        query.where(carConsumable.car.carId.eq(statisticsReqDTO.getCarId()));

        JPQLQuery<StatisticsTotalDTO> dtoQuery = query.select(Projections.bean(StatisticsTotalDTO.class
                ,carConsumable.refConsumableId
                ,carConsumable.gasLitter.sum().as("gasAmount")  // 총 주유량
                ,carConsumable.replacePrice.sum().as("cost")    // 총 정비 비용 & 총 주유 비용
                ,carConsumable.accumKm.max().as("accKm")        // 총 운행 거리
        ));

        List<StatisticsTotalDTO> list = dtoQuery.fetch();

        if(list.size() > 0) {
            int totalAcckm = list.stream().mapToInt(StatisticsTotalDTO::getAccKm).max().getAsInt();

            StatisticsTotalResDTO resultDTO = StatisticsTotalResDTO.builder()
                    .accKm(totalAcckm).build();

            for (StatisticsTotalDTO car : list) {
                if(car.getRefConsumableId().equals(ConsumableType.GAS.getType()))
                {
                    resultDTO.setGasAmount(car.getGasAmount());
                    resultDTO.setGasCost(car.getCost());
                }
                else if(car.getRefConsumableId().equals(ConsumableType.REPAIR.getType())){
                    resultDTO.setRepairCost(car.getCost());
                }
            }

            log.error(resultDTO.toString());

            return resultDTO;
        }

        return null;
    }

}
