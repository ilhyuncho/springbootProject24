package com.example.cih.service.shop;

public interface OrderService {

    Long order(String userName, Long itemId, int count) throws Exception;
}
