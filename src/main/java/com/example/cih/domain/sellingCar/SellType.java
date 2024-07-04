package com.example.cih.domain.sellingCar;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum SellType {
    INIT(0,"초기값"),
    SELL_AUCTION(1, "경매"),
    SELL_DIRECT(2, "직접 거래");

    private final static Map<Integer, SellType> valueMap = Arrays.stream(SellType.values())
            .collect(Collectors.toMap(SellType::getValue, Function.identity()));

    private final Integer value;
    private final String name;

    SellType(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    public static SellType fromValue(Integer value) {
//        if (value == null) {
//            throw new IllegalArgumentException("value is null");
//        }
        return valueMap.get(value);
    }
    public Integer getValue(){
        return value;
    }
}
