package com.example.cih.service.cart;

import com.example.cih.common.exception.ItemNotFoundException;
import com.example.cih.domain.notification.EventNotification;
import com.example.cih.domain.notification.EventType;
import com.example.cih.domain.shop.*;
import com.example.cih.domain.user.User;
import com.example.cih.domain.cart.Cart;
import com.example.cih.dto.cart.CartReqDTO;
import com.example.cih.domain.cart.CartRepository;
import com.example.cih.dto.cart.CartDetailResDTO;
import com.example.cih.dto.shop.ItemOptionResDTO;
import com.example.cih.service.common.CommonUtils;
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
                            .build();

                    if(cart.getItemOptionId1() > 0){
                        cartDTO.getListItemOption().add(getItemOptionInfo(cart.getItemOptionId1()));
                    }
                    if(cart.getItemOptionId2() > 0){
                        cartDTO.getListItemOption().add(getItemOptionInfo(cart.getItemOptionId2()));
                    }

                    // 아이템 이미지 파일 정보 매핑 ( 대표 이미지 만 )
                    cart.getShopItem().getItemImageSet()
                            .stream().filter(image -> image.getImageOrder() == 0)
                            .peek(log::error)
                            .forEach(image -> {
                                cartDTO.addImage(image.getUuid(), image.getFileName(), image.getImageOrder());
                            });

                    return cartDTO;
        }).collect(Collectors.toList());

        return listDTO;
    }
    @Override
    public void addCart(CartReqDTO cartReqDTO, String userName) {

        User user = userService.findUser(userName);

        ShopItem shopItem = shopItemRepository.findByItemName(cartReqDTO.getItemName())
                .orElseThrow(() -> new ItemNotFoundException("해당 상품이 존재하지않습니다"));

        // 이벤트 체크
        EventNotification event = notificationService.getNowDoingEventInfo(EventType.EVENT_BUY_ITEM_DISCOUNT);

        // 회원 등급, 이벤트 여부에 따라 아이템 가격 계산
        int discountPrice = CommonUtils.calcDiscountPrice(user, shopItem, event);

        Cart cart = Cart.builder()
                .shopItem(shopItem)
                .itemCount(cartReqDTO.getItemCount())
                .discountPrice(discountPrice)
                .user(user)
                .isActive(true)
                .metricWeight(10)   // 학습용
                // 아이템 옵션 set
                .itemOptionId1(Long.valueOf(cartReqDTO.getItemOptionList().get(0).getOptionValue()))
                .itemOptionId2(cartReqDTO.getItemOptionList().size() > 1 ?
                        Long.parseLong(cartReqDTO.getItemOptionList().get(1).getOptionValue()) : 0L )
                .build();

        cartRepository.save(cart);
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

    public ItemOptionResDTO getItemOptionInfo(Long itemOptionId){
        ItemOption itemOption = itemOptionRepository.findById(itemOptionId)
                .orElseThrow(() -> new NoSuchElementException("아이템 옵션 정보가 존재하지않습니다"));

        return ItemOptionResDTO.builder()
                .itemOptionId(itemOption.getItemOptionId())
                .optionName(itemOption.getOption1())
                .optionType(itemOption.getType().getName())
                .build();
    }
}
