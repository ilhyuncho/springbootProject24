package com.example.cih.common.util;

import lombok.extern.log4j.Log4j2;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Log4j2
public class Util {

    private Util(){
        // 인스턴스화를 막으려고 private 생성자를 사용
        throw new AssertionError();
    };

    public static String createRandomName(String preString){
        int randomValue = (new Random().nextInt(1000));

        return preString + randomValue;
    }
    public static Stream<UUID> createUUID(long count){
        return Stream.generate(UUID::randomUUID)
                .limit(count);
    }

    public static IntStream createRandomStream(long size, int startIndex, int endIndex  ){
        Random random = new Random();
        // size 개 생성, startIndex ~ endIndex-1 까지

        return random.ints(size, startIndex, endIndex);
    }

    public static LocalTime convertLocalTime(LocalDateTime localDateTime){
        LocalTime localTime = localDateTime.query(LocalTime::from);
        return localTime;
    }

    public static LocalDate convertLocalDate(LocalDateTime localDateTime){
        LocalDate localDate = localDateTime.query(java.time.LocalDate::from);
        return localDate;
    }

    public static LocalDateTime convertStringToLocalDateTime(String str){
        LocalDate date = LocalDate.parse(str);
        LocalDateTime now = LocalDateTime.now();

        return LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(),
                0, 0, 0);

//        return LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(),
//                                now.getHour(), now.getMinute(), now.getSecond() );
    }

    public static String convertLocalDateTimeToString(LocalDateTime localDateTime){

        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH:mm:ss"));
    }

    // 두 날짜 사이의 날짜 구하기
    public static List<LocalDate> getDatesBetweenTwoDates(LocalDate startDate, LocalDate endDate) {


        // 두 날짜 사이에 해당되는 날짜 구하기 ( 2021-09-30 ~ 2021-10-04 )
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/d");
//        LocalDate startDate = LocalDate.parse("2021/09/30", formatter);
//        LocalDate endDate = LocalDate.parse("2021/10/05", formatter);
//
//        Util.getDatesBetweenTwoDates(startDate, endDate).forEach(log::error);

        return startDate.datesUntil(endDate)
                .collect(Collectors.toList());
    }

}
