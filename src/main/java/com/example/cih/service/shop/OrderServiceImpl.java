package com.example.cih.service.shop;

import com.example.cih.common.exception.ItemNotFoundException;
import com.example.cih.common.exception.orderNotFoundException;
import com.example.cih.domain.cart.Cart;
import com.example.cih.domain.cart.CartRepository;
import com.example.cih.domain.shop.*;
import com.example.cih.domain.user.User;
import com.example.cih.domain.delivery.Delivery;
import com.example.cih.domain.user.UserAddressBook;
import com.example.cih.domain.user.UserAddressBookRepository;
import com.example.cih.dto.PageRequestDTO;
import com.example.cih.dto.PageResponseDTO;
import com.example.cih.dto.order.OrderDeliveryResDTO;
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
import java.util.Map;
import java.util.function.Function;
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
    private final UserService userService;
    private final ItemOptionService itemOptionService;
    private final UserAddressBookRepository userAddressBookRepository;

    @Override
    public Long createOrder(User user, OrderReqDTO orderReqDTO){
        // 배송 정보 생성
        Delivery delivery = new Delivery(user.getAddress());

        UserAddressBook userAddressBook = userAddressBookRepository.findById(orderReqDTO.getUserAddressBookId())
                .orElseThrow(() -> new ItemNotFoundException("배송 주소 정보가 존재하지않습니다"));

        // 상세 구매 아이템 정보 생성
        List<OrderItem> listOrderItem = orderReqDTO.getListOrderDetail().stream().map(orderDetailDTO -> {

            if( orderDetailDTO.getCartId() != null){  // 장바구니를 통해서 주문시
                Cart cart = cartRepository.findById(orderDetailDTO.getCartId())
                        .orElseThrow(() -> new ItemNotFoundException("장바구니 정보가 존재하지않습니다"));
                // 장바구니 정보 비활성화
                cart.changeIsActive(false);
            }

            ShopItem shopItem = shopItemRepository.findById(orderDetailDTO.getItemId())
                    .orElseThrow(() -> new ItemNotFoundException("해당 상품이 존재하지않습니다"));

            // 주문 상품 생성
            return OrderItem.createOrderItem(orderDetailDTO, shopItem);

        }).collect(Collectors.toList());

        Order order = Order.createOrder(user, userAddressBook, orderReqDTO, listOrderItem);
        Order save = orderRepository.save(order);

        return save.getOrderId();
    }

    @Override
    public PageResponseDTO<OrderItemResDTO> getOrderAll(PageRequestDTO pageRequestDTO, String userName) {
//        String[] types = pageRequestDTO.getTypes();
//        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable("orderItemId");

        User user = userService.findUser(userName);

        List<Order> orderList = orderRepository.findByUser(user);

        Map<Long, Order> orderMap = orderList.stream()
                .collect(Collectors.toMap(Order::getOrderId, Function.identity()));

        Page<OrderItem> resultOrderItem = orderItemRepository.findByOrders(orderList, pageable);

        List<OrderItemResDTO> listDTO = resultOrderItem.getContent().stream().map(orderItem -> {

                OrderItemResDTO itemDTO = OrderItemResDTO.builder()
                        .orderId(orderItem.getOrder().getOrderId())
                        .orderItemId(orderItem.getOrderItemId())
                        .orderCount(orderItem.getOrderCount())
                        .deliveryStatus(orderMap.get(orderItem.getOrder().getOrderId()).getDeliveryStatus().getName())
                        .shopItemId(orderItem.getShopItem().getShopItemId())
                        .itemName(orderItem.getShopItem().getItemName())
                        .orderPrice(orderItem.getOrderPrice())
                        .orderDate(orderMap.get(orderItem.getOrder().getOrderId()).getOrderTime().toLocalDate())
                        .build();

                // 아이템 옵션 set
                itemDTO.getListItemOption().addAll(itemOptionService.getListItemOptionInfo(orderItem.getListOptionId()));

                // 아이템 이미지 파일 정보 매핑 ( 대표 이미지 만 )
                orderItem.getShopItem().getItemImageSet()
                        .stream().filter(image -> image.getImageOrder() == 0)
                        .peek(log::error)
                        .forEach(image -> {
                            itemDTO.addImage(image.getUuid(), image.getFileName(), image.getImageOrder());
                        });


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
    public void cancelOrder(Long orderItemId) {

        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> {
                    log.info("User expected to delete cart but was empty. orderId = '{}',"
                            , orderItemId);
                    return new orderNotFoundException("해당 주문 아이템 존재 하지 않습니다");
                });

        log.error("cancelOrder" + orderItem.getOrderItemId());
        orderItem.getOrder().changeDeliveryStatus(DeliveryStatus.DELIVERY_CANCEL);
       // orderItem.changeDeliveryStatus(DeliveryStatus.DELIVERY_CANCEL);
    }

    @Override
    public OrderDeliveryResDTO getOrderDeliveryProcess(Long OrderId) {

        Order order = orderRepository.findById(OrderId)
                .orElseThrow(() -> new ItemNotFoundException("주문 정보가 존재하지않습니다"));

        UserAddressBook userAddressBook = order.getUserAddressBook();

        OrderDeliveryResDTO orderDeliveryResDTO = OrderDeliveryResDTO.builder()
                        .orderId(order.getOrderId())
                        .deliveryStatus(order.getDeliveryStatus().getName())
                        .deliveryName(userAddressBook.getDeliveryName())
                        .recipientName(userAddressBook.getRecipientName())
                        .deliveryRequest(userAddressBook.getDeliveryRequest())
                        .fullAddress(userAddressBook.getAddress().fullAddress())
                        .recipientPhoneNumber(userAddressBook.getRecipientPhoneNumber())
                        .build();

        log.error(orderDeliveryResDTO);

        return orderDeliveryResDTO;
    }
}
