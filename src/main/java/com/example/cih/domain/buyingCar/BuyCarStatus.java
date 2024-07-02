package com.example.cih.domain.buyingCar;


import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


public enum BuyCarStatus {
    PROPOSE("propose", 1), // 구매 희망 신청
    GET_CAR("getCar",2), // 구매 성공
    FAIL_CAR("failCar", 3), // 구매 실패
    PROPOSE_CANCEL("proposeCancel", 4),    // 구매 희망 취소
    REQUEST_CONSULT("requestConsult",5 ); // 상담 신청

    private final static Map<String, BuyCarStatus> valueMap = Arrays.stream(BuyCarStatus.values())
            .collect(Collectors.toMap(BuyCarStatus::getName, Function.identity()));

    private final String name;
    private final Integer value;

    BuyCarStatus( String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    public static BuyCarStatus fromValue(String value) {
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
