package com.example.cih.domain.user;


public enum UserActionType {
    ACTION_NONE(0),
    ACTION_LOGIN(1),
    ACTION_REG_MY_CAR(2),
    ACTION_REG_SELLING_CAR(3),
    ACTION_SELL_CAR(4);

    private final Integer type;

    UserActionType(Integer type) {
        this.type = type;
    }

}
