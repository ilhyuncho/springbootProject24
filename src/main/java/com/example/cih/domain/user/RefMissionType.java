package com.example.cih.domain.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;


public enum RefMissionType {
    MISSION_NONE(0L), FIRST_LOGIN(1L), DAILY_LOGIN(2L), REGISTER_CAR(3L), SELL_CAR(4L);

    private final Long type;

    private final static Map<Long, RefMissionType> typeMap = Arrays.stream(RefMissionType.values())
            .collect(Collectors.toMap(RefMissionType::getType, Function.identity()));

    RefMissionType(Long type) {
        this.type = type;
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
}
