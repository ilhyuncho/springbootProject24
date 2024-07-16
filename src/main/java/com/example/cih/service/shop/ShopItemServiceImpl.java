package com.example.cih.service.shop;

import com.example.cih.common.exception.ItemNotFoundException;
import com.example.cih.domain.notification.EventNotification;
import com.example.cih.domain.notification.EventType;
import com.example.cih.domain.shop.*;
import com.example.cih.domain.user.User;
import com.example.cih.dto.shop.*;
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
public class ShopItemServiceImpl implements ShopItemService {

    private final ShopItemRepository shopItemRepository;
    private final ItemPriceRepository itemPriceRepository;
    private final NotificationService notificationService;

    @Override
    public ShopItemExtandDTO getItemInfo(Long shopItemId, User user) {  // User는 null 이 올수 있음

        ShopItem shopItem = shopItemRepository.findById(shopItemId)
                .orElseThrow(() -> new ItemNotFoundException("해당 상품 정보가 존재하지않습니다"));

        // 진행 중인 이벤트 체크
        EventNotification event = notificationService.getNowDoingEventInfo(EventType.EVENT_BUY_ITEM_DISCOUNT);
        // 회원 등급, 이벤트 여부에 따라 아이템 가격 계산
        int discountPrice = CommonUtils.calcDiscountPrice(user, shopItem, event);

        ShopItemExtandDTO shopItemExtandDTO = convertShopItemExtandDTO(shopItem);
        shopItemExtandDTO.setDiscountPrice(discountPrice);

        return shopItemExtandDTO;
    }

    @Override
    public List<ShopItemExtandDTO> getAllItems() {    // 관리자 페이지 상품 리스트

        List<ShopItem> listShopItem = shopItemRepository.findAll();

        List<ShopItemExtandDTO> listShopItemDTO = listShopItem.stream()
                .map(ShopItemServiceImpl::convertShopItemExtandDTO)
                .collect(Collectors.toList());

        // 대표 이미지만 필터링 ( ImageOrder = 0 )------------------begin---------------
        for (ShopItemExtandDTO dto : listShopItemDTO) {
            dto.getFileNames().stream()
                    .filter(carImage -> carImage.getImageOrder() != 0)
                    .collect(Collectors.toList())
                    .forEach(x-> dto.getFileNames().remove(x));
        }
        // 대표 이미지만 필터링 ( ImageOrder = 0 )------------------end---------------

        return listShopItemDTO;
    }

    @Override
    public List<ShopItemDTO> getAllItemsForShop() {   // 악세서리 샵 상품 리스트 (심플)

        List<ShopItem> listShopItem = shopItemRepository.findAll();

        return listShopItem.stream()
                .map(ShopItemServiceImpl::convertShopItemDTO)
                .collect(Collectors.toList());
    }
    @Override
    public Long registerItem(ShopItemReqDTO shopItemReqDTO) {

       shopItemRepository.findByItemName(shopItemReqDTO.getItemName())
               .ifPresent(m -> {
                   throw new ItemNotFoundException("해당 상품 정보가 이미 존재 함");
               });

       // 아이템 가격 정책 저장
       ItemPrice itemPrice = ItemPrice.builder()
               .originalPrice(shopItemReqDTO.getOriginalPrice())
               .membershipPercent(shopItemReqDTO.getMembershipPercent())
               .isEventTarget(shopItemReqDTO.getIsEventTarget())
               .build();

        itemPriceRepository.save(itemPrice);

        // ShopItem 정보 저장
        ShopItem shopItem = dtoToEntity(shopItemReqDTO, itemPrice);

        // 아이템 옵션 set
        shopItemReqDTO.getItemOptionList().forEach(itemOptionDTO -> {
            String[] values = itemOptionDTO.getOptionValue().split(",");
            int orderIndex = 0;
            for (String value : values) {

                ItemOption itemOption = ItemOption.builder()
                        .itemOptionType(ItemOptionType.fromValue(Integer.valueOf(itemOptionDTO.getOptionType())))
                        .optionOrder(orderIndex++)
                        .typePriority(itemOptionDTO.getTypePriority())
                        .optionName(value.trim())
                        .shopItem(shopItem)
                        .build();

                shopItem.addItemOption(itemOption);
            }
        });

        ShopItem saveItem = shopItemRepository.save(shopItem);

        return saveItem.getShopItemId();
    }

    @Override
    public void modifyItem(ShopItemReqDTO shopItemReqDTO) {

        ShopItem shopItem = shopItemRepository.findById(shopItemReqDTO.getShopItemId())
                .orElseThrow(() -> new ItemNotFoundException("해당 상품 정보가 존재하지않습니다"));

        // 가격 정책 update
        shopItem.getItemPrice().changePriceInfo(shopItemReqDTO.getOriginalPrice(),
                shopItemReqDTO.getMembershipPercent(), shopItemReqDTO.getIsEventTarget());

        // shopItem 정보 update
        shopItem.updateInfo(shopItemReqDTO.getItemName(), shopItemReqDTO.getStockCount());

        // 첨부파일 update
        shopItem.updateImages(shopItemReqDTO.getFileNames());

        // 아이템 옵션 udpate
        shopItem.updateItemOption(shopItemReqDTO.getItemOptionList());

        shopItemRepository.save(shopItem);
    }

    @Override
    public void deleteItem(Long itemId) {
        shopItemRepository.deleteById(itemId);
    }


    private static ShopItem dtoToEntity(ShopItemReqDTO shopItemReqDTO, ItemPrice itemPrice) {

        ShopItem shopItem = ShopItem.builder()
                .itemName(shopItemReqDTO.getItemName())
                .itemTitle(shopItemReqDTO.getItemTitle())
                .itemDesc(shopItemReqDTO.getItemDesc())
                .itemPrice(itemPrice)
                .stockCount(shopItemReqDTO.getStockCount())
                .build();

        if(shopItemReqDTO.getFileNames() != null){
            shopItemReqDTO.getFileNames().forEach(fileName ->{
                String[] arr = fileName.split("_");
                shopItem.addImage(arr[0], arr[1]);
            });
        }
        return shopItem;
    }

    private static ShopItemDTO entityToDTO(ShopItem shopItem){

        return ShopItemExtandDTO.builder()
                .shopItemId(shopItem.getShopItemId())
                .itemName(shopItem.getItemName())
                .itemTitle(shopItem.getItemTitle())
                .itemDesc(shopItem.getItemDesc())
                .originalPrice(shopItem.getItemPrice().getOriginalPrice())
                .mainImage(shopItem.getMainImageDTO())
                .build();
    }

    private static ShopItemDTO convertShopItemDTO(ShopItem shopItem) {

        return entityToDTO(shopItem);   // 1. sample DTO 셋팅
    }
    private static ShopItemExtandDTO convertShopItemExtandDTO(ShopItem shopItem) {

        // 1. sample DTO 셋팅
        ShopItemDTO shopItemDTO = entityToDTO(shopItem);

        // 2. 확장 DTO 셋팅
        ShopItemExtandDTO shopItemExtandDTO = (ShopItemExtandDTO) shopItemDTO;

        shopItemExtandDTO.setStockCount(shopItem.getStockCount());
        shopItemExtandDTO.setMembershipPercent(shopItem.getItemPrice().getMembershipPercent());
        shopItemExtandDTO.setIsEventTarget(shopItem.getItemPrice().getIsEventTarget());

        ////////////////////////////////////////////////////////////
        // ItemOption Map 정보 가져오기
        SortedMap<ItemOptionType, String> mapItemOption = shopItem.getMapItemOption();
        Map<ItemOptionType, String> mapItemOptionForView = shopItem.getMapItemOptionForView();

        for (ItemOptionType itemOptionType : mapItemOption.keySet()) {
            shopItemExtandDTO.getListOptionType().add(
                    ItemOptionDTO.builder()
                        .optionType(itemOptionType.getName())
                        .optionValue("0-선택안함, " + mapItemOption.get(itemOptionType))
                        .optionValueForView(mapItemOptionForView.get(itemOptionType))
                        .build()
            );
        }

        // ItemImage 셋팅
        if( shopItem.getItemImageSet().size() > 0) {
            shopItem.getItemImageSet().forEach(shopItemImage -> {
                shopItemExtandDTO.addImage(shopItemImage.getUuid(), shopItemImage.getFileName(), shopItemImage.getImageOrder());
            });
        }

        return shopItemExtandDTO;
    }

}
