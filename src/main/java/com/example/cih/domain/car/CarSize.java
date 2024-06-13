package com.example.cih.domain.car;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum CarSize {
    LARGE("대형"),
    MIDDLE_LARGE("준대형"),
    MIDDLE("중형"),

    SMALL_MIDDLE("준중형"),
    SMALL("소형");

    private final static Map<String, CarSize> valueMap = Arrays.stream(CarSize.values())
            .collect(Collectors.toMap(CarSize::getValue, Function.identity()));

    private final String value;  // db의 값으로 변환할때 사용할 열거형의 속성

    CarSize(String value) {
        this.value = value;
    }

    public static CarSize fromValue(String value) {
        if (value == null) {
            throw new IllegalArgumentException("value is null");
        }
        return valueMap.get(value);
    }
    public String getValue(){
        return value;
    }
}

