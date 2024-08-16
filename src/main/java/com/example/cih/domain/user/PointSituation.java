package com.example.cih.domain.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;


public enum PointSituation {
    SITUATION_NONE(0L, "없음"),
    FIRST_LOGIN(1L, "첫 로그인"),
    DAILY_LOGIN(2L, "매일 로그인"),
    REGISTER_CAR(3L, "내차 등록"),
    SELL_CAR(4L, "차 판매 등록"),
    BUY_ITEM_WITH_POINT(5L, "상품 구매 포인트 사용");

    private final Long type;
    private final String typeName;

    private final static Map<Long, PointSituation> typeMap = Arrays.stream(PointSituation.values())
            .collect(Collectors.toMap(PointSituation::getType, Function.identity()));

    PointSituation(Long type, String typeName) {
        this.type = type;
        this.typeName = typeName;
    }

    @JsonCreator
    public static PointSituation fromValue(Long value) {
        return Optional.ofNullable(value)
                .map(typeMap::get)
                .orElseThrow(() -> new IllegalArgumentException("value is not valid"));
    }
    @JsonValue
    public Long getType(){
        return type;
    }

    public String getTypeName(){return typeName;}

}
