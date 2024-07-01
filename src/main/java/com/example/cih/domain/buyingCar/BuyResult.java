package com.example.cih.domain.buyingCar;


import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


public enum BuyResult {
    PROPOSE("propose", 1), // 구매 희망 신청
    GET_CAR("getCar",2), // 구매 성공
    FAIL_CAR("failCar", 3), // 구매 실패
    REQUEST_CONSULT("requestConsult",4 ); // 상담 신청

    private final static Map<String, BuyResult> valueMap = Arrays.stream(BuyResult.values())
            .collect(Collectors.toMap(BuyResult::getName, Function.identity()));

    private final String name;
    private final Integer value;

    BuyResult( String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    public static BuyResult fromValue(String value) {
        if (value == null) {
            throw new IllegalArgumentException("value is null");
        }
        return valueMap.get(value);
    }

    public Integer getValue(){
        return value;
    }
    public String getName(){
        return name;
    }
}
