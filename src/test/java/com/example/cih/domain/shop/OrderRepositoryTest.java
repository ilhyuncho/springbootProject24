package com.example.cih.domain.shop;

import com.example.cih.domain.delivery.Delivery;
import com.example.cih.domain.user.Address;
import com.example.cih.domain.user.User;
import com.example.cih.domain.user.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.annotation.Commit;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2
class OrderRepositoryTest {

    @Autowired OrderRepository orderRepository;
    @Autowired UserRepository userRepository;
    @Autowired ShopItemRepository shopItemRepository;
    @Autowired OrderItemRepository orderItemRepository;

    @Test
    @Transactional
    @Commit
    public void insertOrder(){

        User user = userRepository.findByUserName("member1").orElseGet(
                () -> User.builder()
                        .userName("user1")
                        .address(Address.builder().build())
                        .billingAddress(Address.builder().build())
                        .build()
        );

        ShopItem shopItem1 = shopItemRepository.getById(1L);
        ShopItem shopItem2 = shopItemRepository.getById(2L);

        // 주문 상품 생성
        OrderItem orderItem1 = OrderItem.createOrderItem(shopItem1, 2 );
        OrderItem orderItem2 = OrderItem.createOrderItem(shopItem2, 5 );
        OrderItem orderItem3 = OrderItem.createOrderItem(shopItem2, 8 );

        Order order = Order.builder()
                .user(user)
                .orderDate(LocalDateTime.now())
                .delivery(new Delivery(user.getAddress()))
                .deliveryStatus(DeliveryStatus.DELIVERY_PREPARE)
                .orderItemList(new ArrayList<>())
                .build();

        order.addOrderItem(orderItem1);
        order.addOrderItem(orderItem2);
        order.addOrderItem(orderItem3);

        Order save = orderRepository.save(order);

        Order order1 = orderRepository.findById(save.getOrderId()).get();


        // OrderSequence 컬럼 출력 여부 확인
        Set<String> orderItemNative1 = orderItemRepository.findOrderItemNative(order1.getOrderId());
        for (String s : orderItemNative1) {
            log.error(s.toString());

        }
    }
    @Test
    @Transactional
    @Commit
    public void deleteShopItem() {

        Optional<Order> result = orderRepository.findById(11L);
        Order order = result.get();

        List<OrderItem> byOrder = orderItemRepository.findByOrder(order);
        OrderItem orderItem = byOrder.get(1);   // 두번째 아이템 픽

        log.error(orderItem.getOrderItemId());
        orderItemRepository.deleteByOrderAndShopItem(orderItem.getOrder(), orderItem.getShopItem());

    }

}