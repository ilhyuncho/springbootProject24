package com.example.cih.service.shop;

import com.example.cih.common.exception.ItemNotFoundException;
import com.example.cih.common.util.Util;
import com.example.cih.domain.shop.ItemPrice;
import com.example.cih.domain.shop.ItemPriceRepository;
import com.example.cih.domain.shop.ShopItem;
import com.example.cih.domain.shop.ShopItemRepository;
import com.example.cih.dto.shop.ShopItemReqDTO;
import com.example.cih.dto.shop.ShopItemViewDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
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
    private final ModelMapper modelMapper;

    @Override
    public ShopItemViewDTO findOne(Long shopItemId) {

        ShopItem shopItem = shopItemRepository.findById(shopItemId)
                .orElseThrow(() -> new ItemNotFoundException("해당 상품 정보가 존재하지않습니다"));

        ShopItemViewDTO shopItemViewDTO = entityToDTO(shopItem);

        return shopItemViewDTO;

    }

    @Override
    public List<ShopItemViewDTO> getAllItems() {

        List<ShopItem> shopItemList = shopItemRepository.findAll();

        List<ShopItemViewDTO> shopItemDTOList = shopItemList.stream().
                map(ShopItemServiceImpl::entityToDTO).collect(Collectors.toList());

        return shopItemDTOList;
    }

    @Override
    public Long registerItem(ShopItemReqDTO shopItemReqDTO) {

       shopItemRepository.findByItemName(shopItemReqDTO.getItemName())
               .ifPresent(m -> {
                   throw new ItemNotFoundException("해당 상품 정보가 이미 존재 함");
               });

       ItemPrice itemPrice = ItemPrice.builder()
               .originalPrice(shopItemReqDTO.getOriginalPrice())
               .membershipPercent(shopItemReqDTO.getMembershipPercent())
               .salePercent(shopItemReqDTO.getSalePercent())
               .saleStartDate(Util.convertStringToLocalDateTime(shopItemReqDTO.getSaleStartDate()))
               .saleEndDate(Util.convertStringToLocalDateTime(shopItemReqDTO.getSaleEndDate()))
               .build();

        itemPriceRepository.save(itemPrice);

        ShopItem shopItem = dtoToEntity(shopItemReqDTO, itemPrice);

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
                shopItemReqDTO.getMembershipPercent(), shopItemReqDTO.getSalePercent(),
                Util.convertStringToLocalDateTime(shopItemReqDTO.getSaleStartDate()),
                Util.convertStringToLocalDateTime(shopItemReqDTO.getSaleEndDate()));

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

        if(!shopItemReqDTO.getItemOption1().isBlank()
                || !shopItemReqDTO.getItemOption2().isBlank() ){
            shopItem.addItemOption(shopItemReqDTO.getItemOption1(), shopItemReqDTO.getItemOption2());
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
                .salePercent(shopItem.getItemPrice().getSalePercent())
                .saleStartDate(shopItem.getItemPrice().getSaleStartDate().toLocalDate())
                .saleEndDate(shopItem.getItemPrice().getSaleEndDate().toLocalDate())
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
