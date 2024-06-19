package com.example.cih.domain.notification;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum EventType {
    EVENT_NONE(0,"이벤트 없음"),
    EVENT_BUY_ITEM_DISCOUNT(1,"상품 구매 할인 이벤트"),
    EVENT_SELL_CAR_GIVE_POINT(2,"차량 판매 포인트 지급");

    private final Integer type;
    private final String name;

    private final static Map<Integer, EventType> typeMap = java.util.Arrays.stream(EventType.values())
            .filter(eventType -> eventType.getType() > 0)
            .collect(Collectors.toMap(EventType::getType, Function.identity()));

    EventType(Integer type, String typeName) {
        this.type = type;
        this.name = typeName;
    }

    @JsonCreator
    public static EventType fromValue(Integer value) {
        return Optional.ofNullable(value)
                .map(typeMap::get)
                .orElseThrow(() -> new IllegalArgumentException("value is not valid"));
    }
    @JsonValue
    public Integer getType(){
        return type;
    }

    public String getName(){return name;}

    public static List<EventType>  getAllTypes(){
       return new ArrayList<>(typeMap.values());
    }


}
