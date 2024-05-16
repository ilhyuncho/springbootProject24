package com.example.cih.domain.buyingCar;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


public enum BuyResult {
    PROPOSE(1),
    GET_CAR(2),
    FAIL_CAR(3);

    private final static Map<Integer, BuyResult> valueMap = Arrays.stream(BuyResult.values())
            .collect(Collectors.toMap(BuyResult::getValue, Function.identity()));

    private final Integer value;

    BuyResult(Integer value) {
        this.value = value;
    }

    public Integer getValue(){
        return value;
    }
}
