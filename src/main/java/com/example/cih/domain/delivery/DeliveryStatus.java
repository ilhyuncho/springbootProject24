package com.example.cih.domain.delivery;

import com.example.cih.domain.car.CarSize;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum DeliveryStatus {
    CANCEL(0),
    READY(1),
    MOVING(2),
    COMPLETE(3);

    private final static Map<Integer, DeliveryStatus> valueMap = Arrays.stream(DeliveryStatus.values())
            .collect(Collectors.toMap(DeliveryStatus::getValue, Function.identity()));

    private final Integer value;

    DeliveryStatus(Integer value) {
        this.value = value;
    }

    public static DeliveryStatus fromValue(Integer value) {
//        if (value == null) {
//            throw new IllegalArgumentException("value is null");
//        }
        return valueMap.get(value);
    }
    public Integer getValue(){
        return value;
    }
}
