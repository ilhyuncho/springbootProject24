package com.example.cih.domain.shop;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum ItemOptionType {
    OPTION_NONE(0,"옵션 없음"),
    OPTION_COLOR(1,"색상"),
    OPTION_SIZE(2,"크기"),
    OPTION_TYPE(3,"종류");

    private final Integer type;
    private final String name;

    private final static Map<Integer, ItemOptionType> typeMap = java.util.Arrays.stream(ItemOptionType.values())
            //.filter(itemOptionType -> itemOptionType.getType() > 0)
            .collect(Collectors.toMap(ItemOptionType::getType, Function.identity()));

    ItemOptionType(Integer type, String typeName) {
        this.type = type;
        this.name = typeName;
    }

    @JsonCreator
    public static ItemOptionType fromValue(Integer value) {
        return Optional.ofNullable(value)
                .map(typeMap::get)
                .orElseThrow(() -> new IllegalArgumentException("value is not valid"));
    }
    @JsonValue
    public Integer getType(){
        return type;
    }

    public String getName(){return name;}

    public static List<ItemOptionType> getAllTypes(){
        return new ArrayList<>(typeMap.values());
    }
}
