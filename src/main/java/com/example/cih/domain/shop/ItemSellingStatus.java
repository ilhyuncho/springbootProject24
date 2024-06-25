package com.example.cih.domain.shop;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum ItemSellingStatus {
    STATUS_NONE(0,"상태 없음"),
    STATUS_SELLING(1,"판매중"),
    STATUS_SOLDOUT(2,"품절");

    private final Integer type;
    private final String name;

    private final static Map<Integer, ItemSellingStatus> typeMap = java.util.Arrays.stream(ItemSellingStatus.values())
            .filter(itemOptionType -> itemOptionType.getType() > 0)
            .collect(Collectors.toMap(ItemSellingStatus::getType, Function.identity()));

    ItemSellingStatus(Integer type, String typeName) {
        this.type = type;
        this.name = typeName;
    }

    @JsonCreator
    public static ItemSellingStatus fromValue(Integer value) {
        return Optional.ofNullable(value)
                .map(typeMap::get)
                .orElseThrow(() -> new IllegalArgumentException("value is not valid"));
    }
    @JsonValue
    public Integer getType(){
        return type;
    }

    public String getName(){return name;}

    public static List<ItemSellingStatus> getAllTypes(){
        return new ArrayList<>(typeMap.values());
    }
}
