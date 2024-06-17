package com.example.cih.domain.shop;


public enum DeliveryStatus {
    DELIVERY_NONE(0,"배송 없음"),
    DELIVERY_PREPARE(1,"배송 준비중"),
    DELIVERY_SHIPPING(2,"배송중"),
    DELIVERY_COMPLETE(3, "배송 완료"),
    DELIVERY_CANCEL(4, "주문 취소");

    private final Integer status;
    private final String name;

    DeliveryStatus(Integer status, String name) {
        this.status = status;
        this.name = name;
    }
    public String getName(){return name;}
}
