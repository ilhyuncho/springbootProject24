package com.example.cih.service.shop;

import com.example.cih.common.exception.ItemNotFoundException;
import com.example.cih.domain.shop.*;
import com.example.cih.dto.ImageDTO;
import com.example.cih.dto.shop.*;
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
public class ShopItemServiceNewImpl implements ShopItemService {

    private final ShopItemRepository shopItemRepository;
    private final ItemPriceRepository itemPriceRepository;

    @Override
    public ShopItemExtandDTO getItem(Long shopItemId) {      // 관리자 페이지 상세 정보

        ShopItem shopItem = shopItemRepository.findById(shopItemId)
                .orElseThrow(() -> new ItemNotFoundException("해당 상품 정보가 존재하지않습니다"));

        ShopItemExtandDTO shopItemExtandDTO = convertShopItemExtandDTO(shopItem);

        return shopItemExtandDTO;
    }

    @Override
    public List<ShopItemExtandDTO> getAllItems() {    // 관리자 페이지 상품 리스트

        List<ShopItem> shopItemList = shopItemRepository.findAll();

        List<ShopItemExtandDTO> shopItemDTOList = shopItemList.stream().
                map(ShopItemServiceNewImpl::convertShopItemExtandDTO).collect(Collectors.toList());

        // 대표 이미지만 필터링 ( ImageOrder = 0 )------------------begin---------------
        for (ShopItemExtandDTO car : shopItemDTOList) {
            car.getFileNames().stream()
                    .filter(carImage -> carImage.getImageOrder() != 0)
                    .collect(Collectors.toList())
                    .forEach(x-> car.getFileNames().remove(x));
        }
        // 대표 이미지만 필터링 ( ImageOrder = 0 )------------------begin---------------

        return shopItemDTOList;
    }

    @Override
    public List<ShopItemDTO> getAllItemsForShop() {   // 악세서리 샵 상품 리스트 (심플)

        List<ShopItem> shopItemList = shopItemRepository.findAll();

        List<ShopItemDTO> listDTO = shopItemList.stream()
                .map(ShopItemServiceNewImpl::convertShopItemDTO)
                .collect(Collectors.toList());

        log.error(listDTO);

        return listDTO;
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

        // 1. sample DTO 셋팅
        ShopItemDTO shopItemDTO = entityToDTO(shopItem);

        log.error(shopItemDTO);
        // 메인 이미지만 추출...

//        ImageDTO mainImage = shopItem.getMainImage();
//
//
//
//        if( shopItem.getItemImageSet().size() > 0){
//            ItemImage itemImage = shopItem.getItemImageSet().stream()
//                    .filter(Objects::nonNull)
//                    .filter(shopItemImage -> shopItemImage.getImageOrder() == 0)
//                    .findFirst().get();
//
//            shopItemDTO.addMainImage(itemImage.getUuid(), itemImage.getFileName(), itemImage.getImageOrder());
//        }

        return shopItemDTO;
    }
    private static ShopItemExtandDTO convertShopItemExtandDTO(ShopItem shopItem) {

        // 1. sample DTO 셋팅
        ShopItemDTO shopItemDTO = entityToDTO(shopItem);



        // 2. 확장 DTO 셋팅
        ShopItemExtandDTO shopItemExtandDTO = (ShopItemExtandDTO) shopItemDTO;

        shopItemExtandDTO.setStockCount(shopItem.getStockCount());
        shopItemExtandDTO.setMembershipPercent(shopItem.getItemPrice().getMembershipPercent());
        shopItemExtandDTO.setIsEventTarget(shopItem.getItemPrice().getIsEventTarget());


        // ItemOption Map 정보 가져오기
        Map<ItemOptionType, String> mapItemOption = shopItem.getMapItemOption();

        for (ItemOptionType itemOptionType : mapItemOption.keySet()) {
            shopItemExtandDTO.getListOptionType().add(ItemOptionDTO.builder()
                    .optionType(itemOptionType.getName())
                    .optionValue(mapItemOption.get(itemOptionType)).build());
        }

        // ItemImage 셋팅
        if( shopItem.getItemImageSet().size() > 0) {
            shopItem.getItemImageSet().forEach(shopItemImage -> {
                shopItemExtandDTO.addImage(shopItemImage.getUuid(), shopItemImage.getFileName(), shopItemImage.getImageOrder());
            });
        }

        return shopItemExtandDTO;
    }



    @Override
    public Long registerItem(ShopItemReqDTO shopItemReqDTO) {

       log.error(shopItemReqDTO);
       shopItemRepository.findByItemName(shopItemReqDTO.getItemName())
               .ifPresent(m -> {
                   throw new ItemNotFoundException("해당 상품 정보가 이미 존재 함");
               });

       ItemPrice itemPrice = ItemPrice.builder()
               .originalPrice(shopItemReqDTO.getOriginalPrice())
               .membershipPercent(shopItemReqDTO.getMembershipPercent())
               .isEventTarget(shopItemReqDTO.getIsEventTarget())
               .build();

        itemPriceRepository.save(itemPrice);

        ShopItem shopItem = dtoToEntity(shopItemReqDTO, itemPrice);

        // 아이템 옵션 set
        shopItemReqDTO.getItemOptionList().forEach(itemOptionDTO -> {
            String[] values = itemOptionDTO.getOptionValue().split(",");
            for (String value : values) {

                ItemOption itemOption = ItemOption.builder()
                        .type(ItemOptionType.fromValue(Integer.valueOf(itemOptionDTO.getOptionType())))
                        .option1(value.trim())
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

        // 수정 해야 하는지, 안해야 하는지. 먼저 체크??
        ItemPrice itemPrice = shopItem.getItemPrice();
        itemPrice.changePriceInfo(shopItemReqDTO.getOriginalPrice(),
                shopItemReqDTO.getMembershipPercent(), shopItemReqDTO.getIsEventTarget());

        shopItem.change(shopItemReqDTO.getItemName(), shopItemReqDTO.getOriginalPrice(), shopItemReqDTO.getStockCount());

        // 첨부파일 처리
        shopItem.clearImages();

        if(shopItemReqDTO.getFileNames() != null){
            for (String fileName : shopItemReqDTO.getFileNames() ) {
                String[] index = fileName.split("_");
                shopItem.addImage(index[0], index[1]);
            }
        }

        shopItemRepository.save(shopItem);
    }

    @Override
    public void deleteItem(Long itemId) {
        shopItemRepository.deleteById(itemId);
    }


    private static ShopItem dtoToEntity(ShopItemReqDTO shopItemReqDTO, ItemPrice itemPrice) {

        ShopItem shopItem = ShopItem.builder()
                .itemName(shopItemReqDTO.getItemName())
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



}
