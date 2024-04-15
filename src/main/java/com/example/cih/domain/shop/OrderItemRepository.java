package com.example.cih.domain.shop;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    @Query("select o from OrderItem o where o.order.orderId = :orderItemId")
    Optional<OrderItem> getOrderItemByOrderItemId(@Param("orderItemId") Long orderItemId);


}

