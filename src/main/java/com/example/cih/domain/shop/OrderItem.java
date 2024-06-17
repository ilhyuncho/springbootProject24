package com.example.cih.domain.shop;


import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"shopItem","order"})
@Table(name="OrderItems")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ORDER_ITEM_ID")
    private Long orderItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SHOP_ITEM_ID")
    private ShopItem shopItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_ID")
    private Order order;

    private DeliveryStatus deliveryStatus;            // 배송 상태

    private int orderPrice;
    private int orderCount;

    public static OrderItem createOrderItem(ShopItem shopItem, int count){

        OrderItem orderItem = OrderItem.builder()
                .shopItem(shopItem)
                .deliveryStatus(DeliveryStatus.DELIVERY_PREPARE)
                .orderPrice(shopItem.getPrice())
                .orderCount(count)
                .build();

        shopItem.removeStock(count);
        return orderItem;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void changeDeliveryStatus(DeliveryStatus deliveryStatus){
        this.deliveryStatus = deliveryStatus;
    }

}
