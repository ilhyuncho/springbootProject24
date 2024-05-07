package com.example.cih.domain.auction;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum AuctionStatus {
    INIT(0),
    PROCESSING(1),
    COMPLETE(2),
    CANCEL(3);

    private final static Map<Integer, AuctionStatus> valueMap = Arrays.stream(AuctionStatus.values())
            .collect(Collectors.toMap(AuctionStatus::getValue, Function.identity()));

    private final Integer value;

    AuctionStatus(Integer value) {
        this.value = value;
    }

    public static AuctionStatus fromValue(Integer value) {
//        if (value == null) {
//            throw new IllegalArgumentException("value is null");
//        }
        return valueMap.get(value);
    }
    public Integer getValue(){
        return value;
    }
}
