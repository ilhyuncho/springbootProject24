package com.example.cih.service.shop;

import com.example.cih.domain.shop.*;
import com.example.cih.domain.user.User;
import com.example.cih.domain.user.UserRepository;
import com.example.cih.domain.delivery.Delivery;
import com.example.cih.dto.PageRequestDTO;
import com.example.cih.dto.PageResponseDTO;
import com.example.cih.dto.order.OrderDTO;
import com.example.cih.dto.order.OrderDetailDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ShopItemService shopItemService;
    private final OrderItemRepository orderItemRepository;

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

    @Override
    public PageResponseDTO<OrderDTO> getOrderAll(PageRequestDTO pageRequestDTO, String userName) {

        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable("orderStatus");

        Optional<User> user = userRepository.findByUserName(userName);
        User userInfo = user.orElseThrow();

        Page<Order> result = orderRepository.findByUser(userInfo, pageable );
        List<Order> orderList = result.getContent();

//        List<CarInfoDTO> dtoList = result.getContent().stream()
//                .map(car -> modelMapper.map(car, CarInfoDTO.class)).collect(Collectors.toList());

        List<OrderDTO> orderDTOList = new ArrayList<>();

        for (Order order : orderList) {
            log.error("OrderID-" + order.getOrderId());

            List<OrderItem> orderItemList = order.getOrderItemList();
            for (OrderItem orderItem : orderItemList) {
                log.error("OrderItemID-" + orderItem.getOrderItemId());
                log.error("ShopItem Name-" + orderItem.getShopItem().getName());
                OrderDTO orderDTO = OrderDTO.builder()
                        .orderId(order.getOrderId())
                        .orderStatus(order.getOrderStatus())
                        .orderCount(orderItem.getOrderCount())
                        .shopItemId(orderItem.getShopItem().getShopItemId())
                        .itemName(orderItem.getShopItem().getName())
                        .itemPrice(orderItem.getShopItem().getPrice())
                        .build();

                orderDTOList.add(orderDTO);
            }
        }

        return PageResponseDTO.<OrderDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(orderDTOList)
                .total((int)result.getTotalElements())
                .build();
    }

    @Override
    public OrderDetailDTO getOrderDetail(Long orderId) {
        Optional<OrderItem> result = orderItemRepository.getOrderItemByOrderItemId(orderId);

        if(result.isPresent()){
            OrderItem orderItem = result.get();

            log.error("getOrderDetail : " + orderItem.getShopItem().getName());


            OrderDetailDTO orderDetailDTO = OrderDetailDTO.builder()
                    .orderCount(orderItem.getOrderCount())
                    .itemName(orderItem.getShopItem().getName())
                    .itemPrice(orderItem.getShopItem().getPrice())
                    .build();
            return orderDetailDTO;
        }

        return null;
    }

    @Override
    public Order cancelOrder(Long orderId) throws Exception {

        log.error("cancelOrder:  orderId = " +orderId);
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> {
                    log.info("User expected to delete cart but was empty. orderId = '{}',"
                            , orderId);
                    return new Exception("장바구니가 비어있습니다");
                });

        log.error("cancelOrder" + order.getOrderId());
        orderRepository.delete(order);


        return order;
    }

}
