package com.example.cih.domain.shop;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    @Query("select o from OrderItem o where o.order.orderId = :orderId")
    Optional<OrderItem> getOrderItemByOrderId(@Param("orderId") Long orderId);

    @Query(value = "SELECT ORDER_ITEM_ID, orderPrice, orderCount, OrderSequence FROM OrderItems WHERE ORDER_ID = ?1",
            nativeQuery = true)
    Set<String> findOrderItemNative(Long orderId);

    List<OrderItem> findByOrder(Order order);

    void deleteByOrderAndShopItem(Order order, ShopItem shopItem);


}

