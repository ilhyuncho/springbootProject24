package com.example.cih.service.cart;

import com.example.cih.common.exception.ItemNotFoundException;
import com.example.cih.common.exception.UserNotFoundException;
import com.example.cih.domain.shop.*;
import com.example.cih.domain.user.User;
import com.example.cih.domain.user.UserRepository;
import com.example.cih.dto.PageRequestDTO;
import com.example.cih.dto.PageResponseDTO;
import com.example.cih.domain.cart.Cart;
import com.example.cih.dto.cart.CartDTO;
import com.example.cih.domain.cart.CartRepository;
import com.example.cih.dto.cart.CartResponseDTO;
import com.example.cih.dto.user.UserDTO;
import com.example.cih.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
                .metricWeight(10)   // 학습용
                .build();

        if(0 != cartDTO.getItemOption()){
            ItemOption itemOption = itemOptionRepository.findById(cartDTO.getItemOption())
                    .orElseThrow(() -> new ItemNotFoundException("해당 상품 옵션 정보가 존재하지않습니다"));

            log.error(itemOption.getItemOptionId() + itemOption.getOption1());
        }




        cartRepository.save(cart);

        return cart;
    }

    @Override
    public PageResponseDTO<CartResponseDTO> getCartAll(PageRequestDTO pageRequestDTO, String userName) {

        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable("cartId");

        User user = userService.findUser(userName);

        Page<Cart> result = cartRepository.findByUser(user, pageable );

        // Page는 map을 지원해서 내부 데이터를 다른것으로 변경 가능
        List<CartResponseDTO> cartDTOList = result.map(cart -> CartResponseDTO.builder()
                .cartId(cart.getCartId())
                .shopItemId(cart.getShopItem().getShopItemId())
                .itemName(cart.getShopItem().getItemName())
                .itemCount(cart.getItemCount())
                .option1(cart.getItemOption() != null ? cart.getItemOption().getOption1() : "옵션 없음") // view에 보여줄때 Option 설명으로 변경 해야 할듯 - cih
                .build()).stream()
                .collect(Collectors.toList());

        // List.copyOf 활용 예 ( 불변 객체 리턴 )
        List<CartResponseDTO> unModifyCartDTOList = List.copyOf(cartDTOList);
        // 에러 발생!!!
        //unModifyCartDTOList.add(CartResponseDTO.builder().build());

        // 하지만 list안이 객체라면 수정 가능 하다..( 주의 해서 사용해야 함 )
        //unModifyCartDTOList.get(0).setItemName("update Temp");


//        List<Cart> cartList = result.getContent();
//        List<CartDTO> cartDTOList = new ArrayList<>();
//
//        for (Cart cart : cartList) {
//            log.error("CartID-" + cart.getCartId());
//
//            CartDTO cartDTO = CartDTO.builder()
//                            .cartId(cart.getCartId())
//                            .shopItemId(cart.getShopItem().getShopItemId())
//                            .itemName(cart.getShopItem().getName())
//                            .itemCount(cart.getItemCount())
//                            .itemOption(cart.getItemOption())
//                            .build();
//
//            cartDTOList.add(cartDTO);
//        }

        return PageResponseDTO.<CartResponseDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(unModifyCartDTOList)
                .total((int)result.getTotalElements())
                .build();
    }

    @Override
    public Cart deleteInCart(Long cartId) {

        log.error("deleteInCart:  cartId = " +cartId);
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> {
                    log.info("User expected to delete cart but was empty. cartId = '{}',"
                            , cartId);
                    return new ItemNotFoundException("선택 상품이 없습니다");
                });

        log.error("deleteInCart" + cart.getCartId());
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
