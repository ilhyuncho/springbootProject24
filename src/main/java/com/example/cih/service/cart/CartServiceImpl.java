package com.example.cih.service.cart;

import com.example.cih.common.exception.ItemNotFoundException;
import com.example.cih.domain.shop.*;
import com.example.cih.domain.user.User;
import com.example.cih.domain.user.UserGradeType;
import com.example.cih.domain.user.UserRepository;
import com.example.cih.domain.cart.Cart;
import com.example.cih.dto.cart.CartReqDTO;
import com.example.cih.domain.cart.CartRepository;
import com.example.cih.dto.cart.CartDetailResDTO;
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

    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final ShopItemRepository shopItemRepository;
    private final ItemOptionRepository itemOptionRepository;
    private final UserService userService;

    @Override
    public List<CartDetailResDTO> getCartAll(String userName) {

        User user = userService.findUser(userName);

        List<Cart> result = cartRepository.findByUser(user);

        List<CartDetailResDTO> cartDetailResDTOList = result.stream()
                .filter(Cart::getIsActive)
                .map(cart -> CartDetailResDTO.builder()
                        .cartId(cart.getCartId())
                        .shopItemId(cart.getShopItem().getShopItemId())
                        .itemName(cart.getShopItem().getItemName())
                        .itemCount(cart.getItemCount())
                        .itemPrice(cart.getShopItem().getItemPrice().getOriginalPrice())
                        .discountPrice(cart.getDiscountPrice())
                        .option1(cart.getItemOption() != null ? cart.getItemOption().getOption1() : "옵션 없음") // view에 보여줄때 Option 설명으로 변경 해야 할듯 - cih
                        .build())
                .collect(Collectors.toList());

        return cartDetailResDTOList;
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
    public int calcDiscountPrice(User user, ShopItem shopItem){

        int discountPrice = 0;
        Integer originalPrice = shopItem.getItemPrice().getOriginalPrice();

        if(user.getMGrade() == UserGradeType.GRADE_E
                || user.getMGrade() == UserGradeType.GRADE_S ){

            if(shopItem.getItemPrice().getMembershipPercent() > 0){

                discountPrice = (originalPrice / shopItem.getItemPrice().getMembershipPercent());
            }
            log.error(discountPrice);
        }
        return discountPrice;
    }

    @Override
    public Cart addCart(CartReqDTO cartReqDTO, String userName) {

        User user = userService.findUser(userName);

        ShopItem shopItem = shopItemRepository.findByItemName(cartReqDTO.getItemName())
                .orElseThrow(() -> new ItemNotFoundException("해당 상품이 존재하지않습니다"));

        // 회원 등급, 이벤트 여부에 따라 아이템 가격 계산
        Integer discountPrice = calcDiscountPrice(user, shopItem);

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
