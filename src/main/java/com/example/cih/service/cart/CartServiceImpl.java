package com.example.cih.service.cart;

import com.example.cih.common.exception.ItemNotFoundException;
import com.example.cih.domain.notification.EventNotification;
import com.example.cih.domain.notification.EventType;
import com.example.cih.domain.shop.*;
import com.example.cih.domain.user.User;
import com.example.cih.domain.user.UserGradeType;
import com.example.cih.domain.cart.Cart;
import com.example.cih.dto.cart.CartReqDTO;
import com.example.cih.domain.cart.CartRepository;
import com.example.cih.dto.cart.CartDetailResDTO;
import com.example.cih.service.notification.NotificationService;
import com.example.cih.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;


@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ShopItemRepository shopItemRepository;
    private final ItemOptionRepository itemOptionRepository;
    private final UserService userService;
    private final NotificationService notificationService;

    public int calcDiscountPrice(User user, ShopItem shopItem, EventNotification event){

        int discountPrice = 0;
        Integer originalPrice = shopItem.getItemPrice().getOriginalPrice();

        // 1. 회원 등급 별 할인율 적용
        if(user.getMGrade() == UserGradeType.GRADE_E
                || user.getMGrade() == UserGradeType.GRADE_S ){

            if(shopItem.getItemPrice().getMembershipPercent() > 0){
                discountPrice += (int)(originalPrice * ((double)shopItem.getItemPrice().getMembershipPercent() / 100));
                log.error("등급 할인 : " + discountPrice);
            }
        }
        // 2. 이벤트 할인율 적용
        if(shopItem.getItemPrice().getIsEventTarget()
                && event != null
                && event.getEventValue() > 0){
//            log.error(originalPrice + "," + (double)eventDiscountRate / 100);

            discountPrice += (int)(originalPrice * ((double)event.getEventValue() / 100));
            log.error("이벤트 할인 : " + discountPrice);
        }

        return discountPrice;
    }
    @Override
    public List<CartDetailResDTO> getCartAll(String userName) {
        User user = userService.findUser(userName);
        // 이벤트 체크
        EventNotification event = notificationService.getNowDoingEventInfo(EventType.EVENT_BUY_ITEM_DISCOUNT);

        List<CartDetailResDTO> listDTO =  cartRepository.findByUser(user)
                .stream()
                .filter(Cart::getIsActive)      // 유효한 장바구니 상품 정보만..
                .map(cart -> CartDetailResDTO.builder()
                        .cartId(cart.getCartId())
                        .shopItemId(cart.getShopItem().getShopItemId())
                        .itemName(cart.getShopItem().getItemName())
                        .itemCount(cart.getItemCount())
                        .itemPrice(cart.getShopItem().getItemPrice().getOriginalPrice())
//                        .discountPrice(cart.getDiscountPrice())
                        .discountPrice(calcDiscountPrice(user, cart.getShopItem(), event))

                        .option1(cart.getItemOption() != null ? cart.getItemOption().getOption1() : "옵션 없음") // view에 보여줄때 Option 설명으로 변경 해야 할듯 - cih
                        .build())
                .collect(Collectors.toList());

        return listDTO;
    }

    @Override
    public void modify(CartReqDTO cartReqDTO) {

        Cart cart = cartRepository.findById(cartReqDTO.getCartId())
                .orElseThrow(() -> {
                    log.info("User expected to delete cart but was empty. cartId = '{}',"
                            , cartReqDTO.getCartId());
                    return new ItemNotFoundException("선택 상품이 없습니다");
                });

        cart.changeItemCount(cartReqDTO.getItemCount());

        cartRepository.save(cart);
    }

    // 할인 가격 계산


    @Override
    public Cart addCart(CartReqDTO cartReqDTO, String userName) {

        User user = userService.findUser(userName);

        ShopItem shopItem = shopItemRepository.findByItemName(cartReqDTO.getItemName())
                .orElseThrow(() -> new ItemNotFoundException("해당 상품이 존재하지않습니다"));

        // 이벤트 체크
        EventNotification event = notificationService.getNowDoingEventInfo(EventType.EVENT_BUY_ITEM_DISCOUNT);

        // 회원 등급, 이벤트 여부에 따라 아이템 가격 계산
        Integer discountPrice = calcDiscountPrice(user, shopItem, event);

        Cart cart = Cart.builder()
                .shopItem(shopItem)
                .itemCount(cartReqDTO.getItemCount())
                .discountPrice(discountPrice)
                .user(user)
                .isActive(true)
                .metricWeight(10)   // 학습용
                .build();

        if(0 != cartReqDTO.getItemOption()){
            ItemOption itemOption = itemOptionRepository.findById(cartReqDTO.getItemOption())
                    .orElseThrow(() -> new ItemNotFoundException("해당 상품 옵션 정보가 존재하지않습니다"));
           // log.error(itemOption.getItemOptionId() + itemOption.getOption1());
        }

        cartRepository.save(cart);

        return cart;
    }

    @Override
    public Cart deleteInCart(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> {
                    log.info("User expected to delete cart but was empty. cartId = '{}',"
                            , cartId);
                    return new ItemNotFoundException("선택 상품이 없습니다");
                });

        cartRepository.delete(cart);
        return cart;
    }


}
