package com.example.cih.domain.sellingCar;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum SellType {
    INIT(0,"initType", "초기값"),
    SELL_AUCTION(1,"auctionType", "경매"),
    SELL_CONSULT(2,"consultType", "상담"),
    SELL_DIRECT(3,"directType", "직접 거래");

    private final static Map<String, SellType> valueMap = Arrays.stream(SellType.values())
            .collect(Collectors.toMap(SellType::getType, Function.identity()));

    private final Integer value;
    private final String type;
    private final String typeName;

    SellType(Integer value, String type, String typeName) {
        this.value = value;
        this.type = type;
        this.typeName = typeName;
    }

    public static SellType fromValue(String type) {
//        if (value == null) {
//            throw new IllegalArgumentException("value is null");
//        }
        return valueMap.get(type);
    }
    public Integer getValue(){
        return value;
    }
    public String getType(){
        return type;
    }
    public String getTypeName(){return typeName;}
}
