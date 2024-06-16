package com.example.cih.service.cart;

import com.example.cih.common.exception.ItemNotFoundException;
import com.example.cih.domain.shop.*;
import com.example.cih.domain.user.User;
import com.example.cih.domain.user.UserRepository;
import com.example.cih.domain.cart.Cart;
import com.example.cih.dto.cart.CartDTO;
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
    public Cart addCart(CartDTO cartDTO, String userName) {

        User user = userService.findUser(userName);

        ShopItem shopItem = shopItemRepository.findByItemName(cartDTO.getItemName())
                .orElseThrow(() -> new ItemNotFoundException("해당 상품이 존재하지않습니다"));

        Cart cart = Cart.builder()
                .shopItem(shopItem)
                .itemCount(cartDTO.getItemCount())
                .user(user)
                .isActive(true)
                .metricWeight(10)   // 학습용
                .build();

        if(0 != cartDTO.getItemOption()){
            ItemOption itemOption = itemOptionRepository.findById(cartDTO.getItemOption())
                    .orElseThrow(() -> new ItemNotFoundException("해당 상품 옵션 정보가 존재하지않습니다"));

           // log.error(itemOption.getItemOptionId() + itemOption.getOption1());
        }

        cartRepository.save(cart);

        return cart;
    }

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
                        .itemPrice(cart.getShopItem().getPrice())
                        .option1(cart.getItemOption() != null ? cart.getItemOption().getOption1() : "옵션 없음") // view에 보여줄때 Option 설명으로 변경 해야 할듯 - cih
                        .build())
                .collect(Collectors.toList());

        return cartDetailResDTOList;
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

    @Override
    public void modify(CartDTO cartDTO) {

        Cart cart = cartRepository.findById(cartDTO.getCartId())
                .orElseThrow(() -> {
                    log.info("User expected to delete cart but was empty. cartId = '{}',"
                            , cartDTO.getCartId());
                    return new ItemNotFoundException("선택 상품이 없습니다");
                });

        cart.changeItemCount(cartDTO.getItemCount());

        cartRepository.save(cart);
    }
}
