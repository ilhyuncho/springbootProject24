package com.example.cih.service.shop;

import com.example.cih.common.exception.ItemNotFoundException;
import com.example.cih.domain.shop.*;
import com.example.cih.dto.shop.ShopItemReqDTO;
import com.example.cih.dto.shop.ShopItemViewDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class ShopItemServiceImpl implements ShopItemService {

    private final ShopItemRepository shopItemRepository;
    private final ItemPriceRepository itemPriceRepository;
    private final ItemOptionRepository itemOptionRepository;

    @Override
    public ShopItemViewDTO findItem(Long shopItemId) {

        ShopItem shopItem = shopItemRepository.findById(shopItemId)
                .orElseThrow(() -> new ItemNotFoundException("해당 상품 정보가 존재하지않습니다"));

        return entityToDTO(shopItem);
    }

    @Override
    public List<ShopItemViewDTO> getAllItems() {

        List<ShopItem> shopItemList = shopItemRepository.findAll();

        List<ShopItemViewDTO> shopItemDTOList = shopItemList.stream().
                map(ShopItemServiceImpl::entityToDTO).collect(Collectors.toList());

        // 대표 이미지만 필터링 ( ImageOrder = 0 )
        for (ShopItemViewDTO car : shopItemDTOList) {
            car.getFileNames().stream()
                    .filter(carImage -> carImage.getImageOrder() != 0)
                    .collect(Collectors.toList())
                    .forEach(x-> car.getFileNames().remove(x));
        }

        return shopItemDTOList;
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
                        .type(ItemOptionType.fromValue(itemOptionDTO.getOptionType()))
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

    private static ShopItemViewDTO entityToDTO(ShopItem shopItem) {
        ShopItemViewDTO shopItemViewDTO = ShopItemViewDTO.writeShopItemViewDTOBuilder()
                .shopItemId(shopItem.getShopItemId())
                .itemName(shopItem.getItemName())
                .originalPrice(shopItem.getItemPrice().getOriginalPrice())
                .stockCount(shopItem.getStockCount())
                .membershipPercent(shopItem.getItemPrice().getMembershipPercent())
                .isEventTarget(shopItem.getItemPrice().getIsEventTarget())
                .build();

        // 임시로... cih
        shopItem.getItemOptionSet().forEach(itemOption -> {
            //  log.error(carImage.getUuid()+ carImage.getFileName()+ carImage.getImageOrder());
            shopItemViewDTO.setItemOption1(itemOption.getOption1());
            shopItemViewDTO.setItemOption2(itemOption.getOption2());
        });

        shopItem.getItemImageSet().forEach(shopItemImage -> {
            shopItemViewDTO.addImage(shopItemImage.getUuid(), shopItemImage.getFileName(), shopItemImage.getImageOrder());
        });
        return shopItemViewDTO;
    }

}
