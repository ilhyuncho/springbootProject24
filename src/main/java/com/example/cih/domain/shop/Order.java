package com.example.cih.domain.shop;


import com.example.cih.domain.user.User;
import com.example.cih.domain.user.UserAddressBook;
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
    private Integer totalPrice;             // 원래 상품 가격
    private Integer totalDiscountPrice;     // 할인 금액
    private Integer totalPaymentPrice;      // 실제 결제 금액
    private Integer useMPoint;              // 주문에 사용한 포인트

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @Builder.Default
    @OrderColumn(name = "OrderSequence")    // orderItems테이블에 자동으로 각 주문마다 sequence 컬럼 생성
    private List<OrderItem> orderItemList = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "userAddressBookId")   // 주 테이블(Order)에 외래 키 양방향
    private UserAddressBook userAddressBook;

    private DeliveryStatus deliveryStatus;            // 배송 상태

    @Column(name = "orderTime")
    private LocalDateTime orderTime;    // 주문 시간

    public static Order createOrder(User userInfo, UserAddressBook userAddressBook, OrderReqDTO orderReqDTO, List<OrderItem> listOrderItem){
        Order order = Order.builder()
                .user(userInfo)
                .totalPrice(orderReqDTO.getTotalPrice())
                .totalDiscountPrice(orderReqDTO.getTotalDiscountPrice())
                .totalPaymentPrice(orderReqDTO.getTotalPaymentPrice())
                .deliveryFee(orderReqDTO.getDeliveryFee())
                .useMPoint(orderReqDTO.getUseMPoint())
                .userAddressBook(userAddressBook)
                .orderTime(LocalDateTime.now())
                .deliveryStatus(DeliveryStatus.DELIVERY_PREPARE)
                .orderItemList(new ArrayList<>())
                .build();

        for (OrderItem orderItem : listOrderItem) {
            order.addOrderItem(orderItem);
        }

        // 유저 보유 포인트 차감
        if(orderReqDTO.getUseMPoint() > 0){
            userInfo.minusMPoint(orderReqDTO.getUseMPoint());
        }

        return order;
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItemList.add(orderItem);
        orderItem.setOrder(this);
    }

    public void changeDeliveryStatus(DeliveryStatus deliveryStatus){
        this.deliveryStatus = deliveryStatus;
    }
}
