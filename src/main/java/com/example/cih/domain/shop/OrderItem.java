package com.example.cih.domain.shop;


import com.example.cih.domain.common.ItemOptionEntity;
import com.example.cih.dto.order.OrderDetailDTO;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

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

    private int orderPrice;                           // 실제 결제한 가격
    private int orderCount;

    public static OrderItem createOrderItem(OrderDetailDTO orderDetailDTO, ShopItem shopItem){

        OrderItem orderItem = OrderItem.builder()
                .shopItem(shopItem)
                .orderPrice(shopItem.getItemPrice().getOriginalPrice() - orderDetailDTO.getDiscountPrice() )
                .orderCount(orderDetailDTO.getItemCount())
                .itemOptionId1(orderDetailDTO.getOptionId(0))
                .itemOptionId2(orderDetailDTO.getOptionId(1))
                .build();

        // 해당 아이템 구매 수량 update
        shopItem.addPurchaseCount(orderDetailDTO.getItemCount());

        return orderItem;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

}
