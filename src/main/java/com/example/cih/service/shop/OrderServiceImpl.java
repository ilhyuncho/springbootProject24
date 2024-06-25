package com.example.cih.service.shop;

import com.example.cih.common.exception.ItemNotFoundException;
import com.example.cih.common.exception.orderNotFoundException;
import com.example.cih.domain.cart.Cart;
import com.example.cih.domain.cart.CartRepository;
import com.example.cih.domain.shop.*;
import com.example.cih.domain.user.User;
import com.example.cih.domain.delivery.Delivery;
import com.example.cih.dto.PageRequestDTO;
import com.example.cih.dto.PageResponseDTO;
import com.example.cih.dto.order.OrderItemResDTO;
import com.example.cih.dto.order.OrderReqDTO;
import com.example.cih.dto.order.OrderViewDTO;
import com.example.cih.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;


@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ShopItemRepository shopItemRepository;
    private final CartRepository cartRepository;
    private final OrderItemRepository orderItemRepository;
    private final ItemOptionRepository itemOptionRepository;
    private final UserService userService;

    @Override
    public Long createOrder(String userName, OrderReqDTO orderReqDTO ){

        // 고객 정보 get
        User user = userService.findUser(userName);

        // 배송 정보 생성
        Delivery delivery = new Delivery(user.getAddress());

        // 상세 구매 아이템 정보 생성
        List<OrderItem> listOrderItem = orderReqDTO.getListOrderDetail().stream().map(item -> {

            Cart cart = cartRepository.findById(item.getCartId())
                    .orElseThrow(() -> new ItemNotFoundException("해당 장바구니 정보가 존재하지않습니다"));
            cart.changeIsActive(false);

            ShopItem shopItem = shopItemRepository.findById(item.getItemId())
                    .orElseThrow(() -> new ItemNotFoundException("해당 상품이 존재하지않습니다"));

            // 주문 상품 생성
            return OrderItem.createOrderItem(shopItem, item, cart);


        }).collect(Collectors.toList());


        Order order = Order.createOrder(user, delivery, listOrderItem);

        Order save = orderRepository.save(order);

        return save.getOrderId();
    }

    @Override
    public PageResponseDTO<OrderItemResDTO> getOrderAll(PageRequestDTO pageRequestDTO, String userName) {
        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable("orderItemId");

        User user = userService.findUser(userName);

        List<Order> orderList = orderRepository.findByUser(user);

        Page<OrderItem> resultOrderItem = orderItemRepository.findByOrders(orderList, pageable);

        List<OrderItemResDTO> listDTO = resultOrderItem.getContent().stream().map(orderItem -> {

                    OrderItemResDTO itemDTO = OrderItemResDTO.builder()
                            .orderId(orderItem.getOrder().getOrderId())
                            .orderItemId(orderItem.getOrderItemId())
                            .orderCount(orderItem.getOrderCount())
                            .deliveryStatus(orderItem.getDeliveryStatus().getName())
                            .shopItemId(orderItem.getShopItem().getShopItemId())
                            .itemName(orderItem.getShopItem().getItemName())
                            .orderPrice(orderItem.getOrderPrice())
                            .build();

                    if(orderItem.getItemOptionId1() > 0){
                        ItemOption itemOption1 = itemOptionRepository.findById(orderItem.getItemOptionId1())
                                .orElseThrow(() -> new NoSuchElementException("getCartAll() 해당 옵션1 정보가 존재하지않습니다"));

                        itemDTO.setOptionType1(itemOption1);
                    }
                    if(orderItem.getItemOptionId2() > 0){
                        ItemOption itemOption2 = itemOptionRepository.findById(orderItem.getItemOptionId2())
                                .orElseThrow(() -> new NoSuchElementException("getCartAll() 해당 옵션1 정보가 존재하지않습니다"));

                        itemDTO.setOptionType2(itemOption2);
                    }

                    return itemDTO;
                }
        ).collect(Collectors.toList());

        return PageResponseDTO.<OrderItemResDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(listDTO)
                .total((int)resultOrderItem.getTotalElements())
                .build();
    }

    @Override
    public OrderViewDTO getOrderDetail(Long orderId) {

       OrderItem orderItem = orderItemRepository.getOrderItemByOrderId(orderId)
                .orElseThrow(() -> new ItemNotFoundException("orderItem이 존재하지않습니다"));;

        log.error("getOrderDetail : " + orderItem.getShopItem().getItemName());

        OrderViewDTO orderViewDTO = OrderViewDTO.builder()
                .orderCount(orderItem.getOrderCount())
                .itemName(orderItem.getShopItem().getItemName())
                .itemPrice(orderItem.getShopItem().getItemPrice().getOriginalPrice())
                .build();

        return orderViewDTO;
    }

    @Override
    public void cancelOrder(Long orderItemId){

        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> {
                    log.info("User expected to delete cart but was empty. orderId = '{}',"
                            , orderItemId);
                    return new orderNotFoundException("해당 주문 아이템 존재 하지 않습니다");
                });

        log.error("cancelOrder" + orderItem.getOrderItemId());

        orderItem.changeDeliveryStatus(DeliveryStatus.DELIVERY_CANCEL);
    }

}
