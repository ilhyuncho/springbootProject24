package com.example.cih.domain.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;


public enum RefMissionType {
    MISSION_NONE(0L, "없음"),
    FIRST_LOGIN(1L, "첫 로그인"),
    DAILY_LOGIN(2L, "매일 로그인"),
    REGISTER_CAR(3L, "내차 등록"),
    SELL_CAR(4L, "차 판매 등록");

    private final Long type;
    private final String typeName;

    private final static Map<Long, RefMissionType> typeMap = Arrays.stream(RefMissionType.values())
            .collect(Collectors.toMap(RefMissionType::getType, Function.identity()));

    RefMissionType(Long type, String typeName) {
        this.type = type;
        this.typeName = typeName;
    }

    @JsonCreator
    public static RefMissionType fromValue(Long value) {
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
