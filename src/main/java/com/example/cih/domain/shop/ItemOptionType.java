package com.example.cih.domain.shop;


public enum ItemOptionType {
    OPTION_NONE(0,"배송 없음"),
    OPTION_COLOR(1,"색상"),
    OPTION_SIZE(2,"크기");

    private final Integer type;
    private final String name;

    ItemOptionType(Integer type, String name) {
        this.type = type;
        this.name = name;
    }
    public String getName(){return name;}
}
