package com.example.cih.service.shop;

import com.example.cih.common.exception.ItemNotFoundException;
import com.example.cih.common.exception.orderNotFoundException;
import com.example.cih.domain.cart.Cart;
import com.example.cih.domain.cart.CartRepository;
import com.example.cih.domain.notification.EventNotification;
import com.example.cih.domain.notification.EventType;
import com.example.cih.domain.shop.*;
import com.example.cih.domain.user.User;
import com.example.cih.domain.user.UserAddressBook;
import com.example.cih.domain.user.UserAddressBookRepository;
import com.example.cih.dto.PageRequestDTO;
import com.example.cih.dto.PageResponseDTO;
import com.example.cih.dto.order.*;
import com.example.cih.dto.shop.ItemBuyReqDTO;
import com.example.cih.service.common.CommonUtils;
import com.example.cih.service.notification.NotificationService;
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
    private final UserAddressBookRepository userAddressBookRepository;
    private final OrderTemporaryRepository orderTemporaryRepository;

    private final ItemOptionService itemOptionService;
    private final UserService userService;
    private final NotificationService notificationService;
    @Override
    public Long createOrder(User user, OrderReqDTO orderReqDTO){

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
    public void cancelOrder(Long orderItemId) {

        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> {
                    log.info("User expected to delete cart but was empty. orderId = '{}',"
                            , orderItemId);
                    return new orderNotFoundException("해당 주문 아이템 존재 하지 않습니다");
                });

        log.error("cancelOrder" + orderItem.getOrderItemId());
        orderItem.getOrder().changeDeliveryStatus(DeliveryStatus.DELIVERY_CANCEL);
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
    public OrderTemporaryResDTO getOrderTemporary(Long orderTemporaryId) {

        OrderTemporary orderTemporary = orderTemporaryRepository.findById(orderTemporaryId)
                .orElseThrow(() -> new ItemNotFoundException("OrderTemporary 이 존재하지않습니다"));


        OrderTemporaryResDTO orderTemporaryResDTO = OrderTemporaryResDTO.builder()
                .orderTemporaryId(orderTemporary.getOrderTemporaryId())
                .shopItemId(orderTemporary.getShopItem().getShopItemId())
                .itemName(orderTemporary.getShopItem().getItemName())
                .orderCount(orderTemporary.getItemCount())
                .orderPrice(orderTemporary.getShopItem().getItemPrice().getOriginalPrice())
                .discountPrice(orderTemporary.getDiscountPrice())
                .orderDate(orderTemporary.getRegDate().toLocalDate())
                .build();

        // 아이템 옵션 set
        orderTemporaryResDTO.getListItemOption().addAll(itemOptionService.getListItemOptionInfo(orderTemporary.getListOptionId()));

        // 아이템 이미지 파일 정보 매핑 ( 대표 이미지 만 )
        orderTemporary.getShopItem().getItemImageSet()
                .stream().filter(image -> image.getImageOrder() == 0)
                .peek(log::error)
                .forEach(image -> {
                    orderTemporaryResDTO.addImage(image.getUuid(), image.getFileName(), image.getImageOrder());
                });

        log.error(orderTemporaryResDTO);

        return orderTemporaryResDTO;
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
    public Long addOrderTemporary(ItemBuyReqDTO itemBuyReqDTO, User user) {

        ShopItem shopItem = shopItemRepository.findByItemName(itemBuyReqDTO.getItemName())
                .orElseThrow(() -> new ItemNotFoundException("해당 상품이 존재하지않습니다"));

        // 이벤트 체크
        EventNotification event = notificationService.getNowDoingEventInfo(EventType.EVENT_BUY_ITEM_DISCOUNT);

        // 회원 등급, 이벤트 여부에 따라 아이템 가격 계산
        int discountPrice = CommonUtils.calcDiscountPrice(user, shopItem, event);

        OrderTemporary orderTemporary = OrderTemporary.builder()
                .shopItem(shopItem)
                .itemCount(itemBuyReqDTO.getItemCount())
                .discountPrice(discountPrice)
                .user(user)
                // 아이템 옵션 set
                .itemOptionId1(itemBuyReqDTO.getOptionId(0))
                .itemOptionId2(itemBuyReqDTO.getOptionId(1))
                .build();

        OrderTemporary saveOrderTemporary = orderTemporaryRepository.save(orderTemporary);

        return saveOrderTemporary.getOrderTemporaryId();
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
