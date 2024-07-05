package com.example.cih.domain.shop;


import com.example.cih.domain.user.User;
import com.example.cih.domain.delivery.Delivery;
import com.example.cih.dto.order.OrderReqDTO;
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

    private Integer deliveryFee;
    private Integer totalPrice;
    private Integer totalDiscountPrice;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @Builder.Default
    @OrderColumn(name = "OrderSequence")    // orderItems테이블에 자동으로 각 주문마다 sequence 컬럼 생성
    private List<OrderItem> orderItemList = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "DELIVERY_ID")   // 주 테이블(Order)에 외래 키 양방향
    private Delivery delivery;          // 배송 정보

    @Column(name = "orderTime")
    private LocalDateTime orderTime;    // 주문 시간

    public static Order createOrder(User userInfo, Delivery delivery, OrderReqDTO orderReqDTO, List<OrderItem> listOrderItem){
        Order order = Order.builder()
                .user(userInfo)
                .totalPrice(orderReqDTO.getTotalPrice())
                .totalDiscountPrice(orderReqDTO.getTotalDiscountPrice())
                .deliveryFee(orderReqDTO.getDeliveryFee())
                .orderTime(LocalDateTime.now())
                .delivery(delivery)
                .orderItemList(new ArrayList<>())
                .build();

        for (OrderItem orderItem : listOrderItem) {
            order.addOrderItem(orderItem);
        }

        return order;
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItemList.add(orderItem);
        orderItem.setOrder(this);
    }
}
