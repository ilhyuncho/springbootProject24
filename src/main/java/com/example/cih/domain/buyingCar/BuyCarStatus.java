package com.example.cih.domain.buyingCar;


import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


public enum BuyCarStatus {
    PROPOSE("propose", 1), // 구매 희망 신청
    PROPOSE_CHANGE_PRICE("proposeChangePrice", 2), // 구매 희망 가격 수정 요청
    PROPOSE_CANCEL("proposeCancel", 3),    // 구매 희망 취소
    GET_CAR("getCar",4), // 구매 성공
    FAIL_CAR("failCar", 5), // 구매 실패

    CONSULT_REQUEST("consultRequest",6), // 상담 신청
    CONSULT_CANCEL("consultCancel",7), // 상담 신청 취소

    AUCTION_REQUEST("auctionRequest",8), // 경매 참여
    AUCTION_CANCEL("auctionCancel",9); // 경매 참여 취소

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
