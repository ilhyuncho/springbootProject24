package com.example.cih.service.cart;

import com.example.cih.common.exception.ItemNotFoundException;
import com.example.cih.domain.notification.EventNotification;
import com.example.cih.domain.notification.EventType;
import com.example.cih.domain.shop.*;
import com.example.cih.domain.user.User;
import com.example.cih.domain.cart.Cart;
import com.example.cih.dto.shop.ItemBuyReqDTO;
import com.example.cih.domain.cart.CartRepository;
import com.example.cih.dto.cart.CartDetailResDTO;
import com.example.cih.service.common.CommonUtils;
import com.example.cih.service.notification.NotificationService;
import com.example.cih.service.shop.ItemOptionService;
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
    private final ItemOptionService itemOptionService;
    private final UserService userService;
    private final NotificationService notificationService;


    @Override
    public List<CartDetailResDTO> getCartAll(String userName) {
        User user = userService.findUser(userName);
        // 이벤트 체크
        EventNotification event = notificationService.getNowDoingEventInfo(EventType.EVENT_BUY_ITEM_DISCOUNT);

        List<Cart> result = cartRepository.findByUser(user);

        List<CartDetailResDTO> listDTO = result.stream()
                .filter(Cart::getIsActive)  // 유효한 정보 만..
                .map(cart -> {
                    CartDetailResDTO cartDTO = CartDetailResDTO.builder()
                            .cartId(cart.getCartId())
                            .shopItemId(cart.getShopItem().getShopItemId())
                            .itemName(cart.getShopItem().getItemName())
                            .itemCount(cart.getItemCount())
                            .itemPrice(cart.getShopItem().getItemPrice().getOriginalPrice())
                            .discountPrice(CommonUtils.calcDiscountPrice(user, cart.getShopItem(), event))
                            .isFreeDelivery(cart.getShopItem().isFreeDelivery())
                            .build();

                    // 아이템 옵션 set
                    cartDTO.getListItemOption().addAll(itemOptionService.getListItemOptionInfo(cart.getListOptionId()));

                    // 아이템 이미지 파일 정보 매핑 ( 대표 이미지 만 )
                    cart.getShopItem().getItemImageSet()
                            .stream().filter(image -> image.getImageOrder() == 0)
                            .peek(log::error)
                            .forEach(image -> {
                                cartDTO.addImage(image.getItemImageId(), image.getUuid(), image.getFileName(),
                                        image.getImageOrder(), image.getIsMainImage());
                            });

                    return cartDTO;
        }).collect(Collectors.toList());

        return listDTO;
    }
    @Override
    public Long addCart(ItemBuyReqDTO itemBuyReqDTO, String userName) {

        User user = userService.findUser(userName);

        ShopItem shopItem = shopItemRepository.findByItemName(itemBuyReqDTO.getItemName())
                .orElseThrow(() -> new ItemNotFoundException("해당 상품이 존재하지않습니다"));

        // 이벤트 체크
        EventNotification event = notificationService.getNowDoingEventInfo(EventType.EVENT_BUY_ITEM_DISCOUNT);

        // 회원 등급, 이벤트 여부에 따라 아이템 가격 계산
        int discountPrice = CommonUtils.calcDiscountPrice(user, shopItem, event);

        Cart cart = Cart.builder()
                .shopItem(shopItem)
                .itemCount(itemBuyReqDTO.getItemCount())
                .discountPrice(discountPrice)
                .user(user)
                .isActive(true)
                .metricWeight(10)   // 학습용
                // 아이템 옵션 set
                .itemOptionId1(itemBuyReqDTO.getOptionId(0))
                .itemOptionId2(itemBuyReqDTO.getOptionId(1))

                .build();

        return cartRepository.save(cart).getCartId();
    }
    @Override
    public void modify(ItemBuyReqDTO itemBuyReqDTO) {

        Cart cart = cartRepository.findById(itemBuyReqDTO.getCartId())
                .orElseThrow(() -> {
                    log.info("User expected to delete cart but was empty. cartId = '{}',"
                            , itemBuyReqDTO.getCartId());
                    return new ItemNotFoundException("선택 상품이 없습니다");
                });

        cart.changeItemCount(itemBuyReqDTO.getItemCount());

        cartRepository.save(cart);
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
