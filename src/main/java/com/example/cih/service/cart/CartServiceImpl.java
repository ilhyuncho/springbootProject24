package com.example.cih.service.cart;

import com.example.cih.domain.shop.Order;
import com.example.cih.domain.shop.OrderItemRepository;
import com.example.cih.domain.shop.OrderRepository;
import com.example.cih.domain.user.User;
import com.example.cih.domain.user.UserRepository;
import com.example.cih.dto.PageRequestDTO;
import com.example.cih.dto.PageResponseDTO;
import com.example.cih.domain.cart.Cart;
import com.example.cih.dto.cart.CartDTO;
import com.example.cih.domain.cart.CartRepository;
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
public class CartServiceImpl implements CartService {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;


    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    @Override
    public PageResponseDTO<CartDTO> getCartAll(PageRequestDTO pageRequestDTO, String userName) {

        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable("cartId");

        Optional<User> user = userRepository.findByUserName(userName);
        User userInfo = user.orElseThrow();

        Page<Cart> result = cartRepository.findByUser(userInfo, pageable );
        List<Cart> cartList = result.getContent();

//        List<CarInfoDTO> dtoList = result.getContent().stream()
//                .map(car -> modelMapper.map(car, CarInfoDTO.class)).collect(Collectors.toList());

        List<CartDTO> cartDTOList = new ArrayList<>();

        for (Cart cart : cartList) {
            log.error("CartID-" + cart.getCartId());

            CartDTO cartDTO = CartDTO.builder()
                            .cartId(cart.getCartId())
                            .shopItemId(cart.getShopItem().getShopItemId())
                            .itemName(cart.getShopItem().getName())
                            .itemCount(cart.getItemCount())
                            .itemOption(cart.getItemOption())
                            .build();

            cartDTOList.add(cartDTO);
        }

        return PageResponseDTO.<CartDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(cartDTOList)
                .total((int)result.getTotalElements())
                .build();
    }

    @Override
    public Order deleteInCart(Long orderId) throws Exception {

        log.error("deleteInCart:  orderId = " +orderId);
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> {
                    log.info("User expected to delete cart but was empty. orderId = '{}',"
                            , orderId);
                    return new Exception("장바구니가 비어있습니다");
                });

        log.error("deleteInCart" + order.getOrderId());
        orderRepository.delete(order);


        return order;
    }
}
