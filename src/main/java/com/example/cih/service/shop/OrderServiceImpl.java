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
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalTime;
import java.util.*;
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
    private final NotificationService notificationService;
    @Override
    public Long createOrder(User user, OrderReqDTO orderReqDTO){

        UserAddressBook userAddressBook = userAddressBookRepository.findById(orderReqDTO.getUserAddressBookId())
                .orElseThrow(() -> new ItemNotFoundException("배송 주소 정보가 존재하지않습니다"));

        if(orderReqDTO.getUseMPoint() > 0
                && orderReqDTO.getUseMPoint() < user.getMPoint()){
            throw new IllegalArgumentException("사용 포인트 값이 잘못 되었습니다");
        }

        // 상세 구매 아이템 정보 생성
        List<OrderItem> listOrderItem = orderReqDTO.getListOrderDetail().stream().map(orderDetailDTO -> {

            // 장바구니를 통해서 주문시
            if( orderDetailDTO.getCartId() != null){
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

        // Order 생성
        Order order = Order.createOrder(user, userAddressBook, orderReqDTO, listOrderItem);
        orderRepository.save(order);

        return order.getOrderId();
    }

    @Override
    public void cancelOrder(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> {
                    log.info("User expected to delete cart but was empty. orderId = '{}',", orderId);
                    return new orderNotFoundException("해당 주문 정보가 존재 하지 않습니다");
                });

        order.changeDeliveryStatus(DeliveryStatus.DELIVERY_CANCEL);
    }

    @Override
    public PageResponseDTO<OrderListResDTO> getOrderAll(User user, PageRequestDTO pageRequestDTO) {

        Pageable pageable = pageRequestDTO.getPageable("orderItemId");

        // 고객의 주문 내역 조회
        List<Order> orderList = orderRepository.findByUser(user);

        // 주문 별 상품 구매 내역 조회
        Page<OrderItem> resultOrderItem = orderItemRepository.findByOrders(orderList, pageable);

        Map<Order, List<OrderItem>> mapOrderItem = resultOrderItem.getContent().stream()
                .collect(Collectors.groupingBy(OrderItem::getOrder,
                        Collectors.mapping(Function.identity(), Collectors.toList())));

        List<OrderListResDTO> listResDTO = new ArrayList<>();

        for (Order order : mapOrderItem.keySet()) {

            OrderListResDTO orderListResDTO = OrderListResDTO.builder()
                    .orderId(order.getOrderId())
                    .deliveryStatus(order.getDeliveryStatus().getName())
                    .orderDate(order.getOrderTime().toLocalDate())
                    .orderPrice(order.getTotalPrice())
                    .paymentPrice(order.getTotalPaymentPrice())
                    .build();

            // 주문 타이틀 생성
            String itemNames = mapOrderItem.get(order).stream()
                    .map(orderItem -> orderItem.getShopItem().getItemName())
                    .reduce("", (a, b) -> a + b + ",")
                    .replaceFirst(".$", "");    // 정규 표현식을 활용한 마지막 문자 제거
                                                                // . -> 모든 문자, $ -> 문자열의 끝
            orderListResDTO.setItemName(itemNames);

            // 아이템 이미지 파일 정보 매핑 ( 대표 이미지 만 )
            mapOrderItem.get(order).forEach(orderItem -> {
                orderItem.getShopItem().getItemImageSet()
                        .stream().filter(ItemImage::getIsMainImage)
                        .forEach(image -> {
                            orderListResDTO.addImage(image.getItemImageId(), image.getUuid(), image.getFileName(),
                                    image.getImageOrder(), image.getIsMainImage());
                        });
            });

            listResDTO.add(orderListResDTO);
        }

        // 주문 순서 역순으로 정렬
        listResDTO.sort(Comparator.comparing(OrderListResDTO::getOrderId).reversed());

        return PageResponseDTO.<OrderListResDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(listResDTO)
                .total((int)resultOrderItem.getTotalElements()) // 수정 해야 함!!!
                .build();
    }
    @Override
    public List<OrderItemResDTO> getOrderDetail(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ItemNotFoundException("order정보가 존재하지않습니다"));

        List<OrderItem> listOrderItem = orderItemRepository.findByOrder(order);

        List<OrderItemResDTO> listOrderItemResDTO = listOrderItem.stream().map(orderItem -> {
            OrderItemResDTO itemDTO = OrderItemResDTO.builder()
                    .orderId(orderItem.getOrder().getOrderId())
                    .orderItemId(orderItem.getOrderItemId())
                    .orderCount(orderItem.getOrderCount())
                    .shopItemId(orderItem.getShopItem().getShopItemId())
                    .itemName(orderItem.getShopItem().getItemName())
                    .orderPrice(orderItem.getOrderPrice())
                    .deliveryStatus(order.getDeliveryStatus().getName())
                    .orderDate(order.getOrderTime().toLocalDate())
                    .build();

            // 아이템 옵션 set
            itemDTO.getListItemOption().addAll(itemOptionService.getListItemOptionInfo(orderItem.getListOptionId()));

            // 아이템 이미지 파일 정보 매핑 ( 대표 이미지 만 )
            orderItem.getShopItem().getItemImageSet()
                    .stream().filter(image -> image.getImageOrder() == 0)
                    .peek(log::error)
                    .forEach(image -> {
                        itemDTO.addImage(image.getItemImageId(), image.getUuid(), image.getFileName(),
                                image.getImageOrder(), image.getIsMainImage());
                    });

            return itemDTO;
        }).collect(Collectors.toList());

        return listOrderItemResDTO;
    }

    @Override
    public OrderTemporaryResDTO getOrderTemporary(Long orderTemporaryId) {

        OrderTemporary orderTemporary = orderTemporaryRepository.findById(orderTemporaryId)
                .orElseThrow(() -> new ItemNotFoundException("OrderTemporary 이 존재하지않습니다"));

        // 주문 내역 유효 기간 체크
        LocalTime expiredTime = orderTemporary.getExpiredDate().toLocalTime();
        if(CommonUtils.checkTimeOver(expiredTime)){
           // throw new OrderExpiredException("주문서 만료 기간이 지났습니다");
            return null;
        }

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
                    orderTemporaryResDTO.addImage(image.getItemImageId(), image.getUuid(), image.getFileName(),
                            image.getImageOrder(), image.getIsMainImage());
                });

        return orderTemporaryResDTO;
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

        orderTemporaryRepository.save(orderTemporary);

        return orderTemporary.getOrderTemporaryId();
    }

    @Override
    public OrderDeliveryResDTO getOrderDeliveryProcess(Long OrderId) {

        Order order = orderRepository.findById(OrderId)
                .orElseThrow(() -> new ItemNotFoundException("주문 정보가 존재하지않습니다"));

        UserAddressBook userAddressBook = order.getUserAddressBook();

        return OrderDeliveryResDTO.builder()
                        .orderId(order.getOrderId())
                        .deliveryStatus(order.getDeliveryStatus().getName())
                        .deliveryName(userAddressBook.getDeliveryName())
                        .recipientName(userAddressBook.getRecipientName())
                        .deliveryRequest(userAddressBook.getDeliveryRequest())
                        .fullAddress(userAddressBook.getAddress().fullAddress())
                        .recipientPhoneNumber(userAddressBook.getRecipientPhoneNumber())
                        .build();
    }
}
