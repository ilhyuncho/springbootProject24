package com.example.cih.domain.user;


public enum UserActionType {
    ACTION_NONE(0),
    ACTION_LOGIN(1),
    ACTION_REG_MY_CAR(2),       // 내 차량 등록
    ACTION_REG_SELLING_CAR(3),  // 차량 판매 등록
    ACTION_SELL_CAR(4);            // 차량 판매 완료

    private final Integer type;

    UserActionType(Integer type) {
        this.type = type;
    }

}
