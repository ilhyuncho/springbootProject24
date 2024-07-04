package com.example.cih.domain.shop;


import com.example.cih.domain.common.ItemOptionEntity;
import com.example.cih.dto.order.OrderDetailDTO;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"shopItem","order"})
@Table(name="OrderItems")
public class OrderItem extends ItemOptionEntity{
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

    private int orderPrice;                           // 실제 결제한 가격
    private int orderCount;

    public static OrderItem createOrderItem(ShopItem shopItem, OrderDetailDTO orderDetailDTO, List<Long> listItemOption){

        OrderItem orderItem = OrderItem.builder()
                .shopItem(shopItem)
                .deliveryStatus(DeliveryStatus.DELIVERY_PREPARE)
                .orderPrice(shopItem.getItemPrice().getOriginalPrice() - orderDetailDTO.getDiscountPrice() )
                .orderCount(orderDetailDTO.getItemCount())
                .itemOptionId1(listItemOption.get(0))
                .itemOptionId2(listItemOption.size() > 1 ? listItemOption.get(1) : 0L)
                .build();

        // 해당 아이템 제고 수량 차감
        shopItem.removeStock(orderDetailDTO.getItemCount());

        return orderItem;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void changeDeliveryStatus(DeliveryStatus deliveryStatus){
        this.deliveryStatus = deliveryStatus;
    }
}
