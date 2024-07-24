package com.example.cih.domain.sellingCar;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum SellingCarStatus {
    INIT(0, "상태 없음"),
    PROCESSING(1, "판매 중"),
    COMPLETE(2, "판매 완료"),
    CANCEL(3, "판매 취소");

    private final static Map<Integer, SellingCarStatus> valueMap = Arrays.stream(SellingCarStatus.values())
            .collect(Collectors.toMap(SellingCarStatus::getValue, Function.identity()));

    private final Integer value;
    private final String name;

    SellingCarStatus(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    public static SellingCarStatus fromValue(Integer value) {
//        if (value == null) {
//            throw new IllegalArgumentException("value is null");
//        }
        return valueMap.get(value);
    }
    public Integer getValue(){
        return value;
    }
    public String getName(){
        return name;
    }
}
