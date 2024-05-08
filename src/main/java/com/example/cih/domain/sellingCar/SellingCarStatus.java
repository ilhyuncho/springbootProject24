package com.example.cih.domain.sellingCar;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum SellingCarStatus {
    INIT(0),
    PROCESSING(1),
    COMPLETE(2),
    CANCEL(3);

    private final static Map<Integer, SellingCarStatus> valueMap = Arrays.stream(SellingCarStatus.values())
            .collect(Collectors.toMap(SellingCarStatus::getValue, Function.identity()));

    private final Integer value;

    SellingCarStatus(Integer value) {
        this.value = value;
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
}
