package com.example.cih.service.shop;

import com.example.cih.domain.shop.Order;
import com.example.cih.domain.shop.OrderRepository;
import com.example.cih.domain.user.User;
import com.example.cih.domain.user.UserRepository;
import com.example.cih.domain.delivery.Delivery;
import com.example.cih.domain.shop.OrderItem;
import com.example.cih.domain.shop.ShopItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;


@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ShopItemService shopItemService;


    @Override
    public Long order(String userName, Long itemId, int count) throws Exception {

        log.error(userName + "," + itemId + "," + count);
        // 고객 정보 get
        Optional<User> user = userRepository.findByUserName(userName);
        User userInfo = user.orElseThrow();

        ShopItem shopItem = shopItemService.findOne(itemId);

        Delivery delivery = new Delivery(userInfo.getAddress());
        // 주문 상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(shopItem, count );


        Order order = Order.createOrder(userInfo, delivery, orderItem);

        orderRepository.save(order);

        return order.getOrderId();
    }

}
