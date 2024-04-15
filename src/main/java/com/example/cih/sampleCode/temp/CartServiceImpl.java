package com.example.cih.sampleCode.temp;

import com.example.cih.domain.car.Car;
import com.example.cih.domain.shop.Order;
import com.example.cih.domain.shop.OrderItem;
import com.example.cih.domain.shop.OrderItemRepository;
import com.example.cih.domain.shop.OrderRepository;
import com.example.cih.domain.user.User;
import com.example.cih.domain.user.UserRepository;
import com.example.cih.dto.PageRequestDTO;
import com.example.cih.dto.PageResponseDTO;
import com.example.cih.dto.car.CarInfoDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.thymeleaf.templateresolver.ITemplateResolver;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class CartServiceImpl implements CartService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    @Override
    public PageResponseDTO<CartDTO> getCartAll(PageRequestDTO pageRequestDTO, String userName) {

        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable("orderStatus");

        Optional<User> user = userRepository.findByUserName(userName);
        User userInfo = user.orElseThrow();

        Page<Order> result = orderRepository.findByUser(userInfo, pageable );
        List<Order> orderList = result.getContent();

//        List<CarInfoDTO> dtoList = result.getContent().stream()
//                .map(car -> modelMapper.map(car, CarInfoDTO.class)).collect(Collectors.toList());

        List<CartDTO> cartDTOList = new ArrayList<>();

        for (Order order : orderList) {
            log.error("OrderID-" + order.getOrderId());

            List<OrderItem> orderItemList = order.getOrderItemList();
            for (OrderItem orderItem : orderItemList) {
                log.error("OrderItemID-" + orderItem.getOrderItemId());
                log.error("ShopItem Name-" + orderItem.getShopItem().getName());
                CartDTO cartDTO = CartDTO.builder()
                        .orderId(order.getOrderId())
                        .orderStatus(order.getOrderStatus())
                        .orderCount(orderItem.getOrderCount())
                        .shopItemId(orderItem.getShopItem().getShopItemId())
                        .itemName(orderItem.getShopItem().getName())
                        .itemPrice(orderItem.getShopItem().getPrice())
                        .build();

                cartDTOList.add(cartDTO);
            }
        }

        return PageResponseDTO.<CartDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(cartDTOList)
                .total((int)result.getTotalElements())
                .build();
    }

    @Override
    public CartDetailDTO getOrderDetail(Long orderId) {

        Optional<OrderItem> result = orderItemRepository.getOrderItemByOrderItemId(orderId);

        if(result.isPresent()){
            OrderItem orderItem = result.get();

            log.error("getOrderDetail : " + orderItem.getShopItem().getName());


            CartDetailDTO cartDetailDTO = CartDetailDTO.builder()
                    .orderCount(orderItem.getOrderCount())
                    .itemName(orderItem.getShopItem().getName())
                    .itemPrice(orderItem.getShopItem().getPrice())
                    .build();
            return cartDetailDTO;
        }

        return null;
    }
}
