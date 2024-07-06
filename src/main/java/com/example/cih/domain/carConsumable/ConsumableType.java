package com.example.cih.domain.carConsumable;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
public enum ConsumableType {
    GAS(1,"gas"),
    REPAIR(2, "repair");

    private final Integer type;
    private final String name;

    private final static Map<String, ConsumableType> valueMap = Arrays.stream(ConsumableType.values())
            .collect(Collectors.toMap(ConsumableType::getName, Function.identity()));

    ConsumableType( Integer type, String name ) {
        this.type = type;
        this.name = name;
    }

    public static List<ConsumableType> getListConsumableType(){
        return Arrays.stream(ConsumableType.values()).collect(Collectors.toList());
    }

    public static ConsumableType fromValue(String value) {
        if (value == null) {
            throw new IllegalArgumentException("value is null");
        }
        return valueMap.get(value);
    }

    public Integer getType(){
        return type;
    }
    public String getName(){
        return name;
    }

}
