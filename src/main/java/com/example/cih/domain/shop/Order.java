package com.example.cih.domain.shop;


import com.example.cih.domain.user.User;
import com.example.cih.domain.delivery.Delivery;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name="orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ORDER_ID")
    private Long orderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uId")
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItemList = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "DELIVERY_ID")
    private Delivery delivery;          // 배송 정보

    @Column(name = "orderDate")
    private LocalDateTime orderDate;    // 주문 시간

    private int orderStatus;            // 주문 상태

    public static Order createOrder(User userInfo, Delivery delivery, OrderItem... orderItems){
        Order order = Order.builder()
                .user(userInfo)
                .orderDate(LocalDateTime.now())
                .delivery(delivery)
                .orderStatus(1)
                .orderItemList(new ArrayList<>())
                .build();

        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }

        return order;
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItemList.add(orderItem);
        orderItem.setOrder(this);
    }
}
