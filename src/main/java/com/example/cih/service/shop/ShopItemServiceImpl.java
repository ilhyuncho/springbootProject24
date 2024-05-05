package com.example.cih.service.shop;

import com.example.cih.common.exception.ItemNotFoundException;
import com.example.cih.domain.shop.ShopItem;
import com.example.cih.domain.shop.ShopItemRepository;
import com.example.cih.dto.shop.ShopItemDTO;
import com.example.cih.dto.shop.ShopItemViewDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class ShopItemServiceImpl implements ShopItemService {

    private final ShopItemRepository shopItemRepository;
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
    public Long registerItem(ShopItemDTO shopItemDTO) {

       shopItemRepository.findByItemName(shopItemDTO.getItemName())
               .ifPresent(m -> {
                   throw new ItemNotFoundException("해당 상품 정보가 이미 존재 함");
               });

        ShopItem shopItem = dtoToEntity(shopItemDTO);

        ShopItem saveItem = shopItemRepository.save(shopItem);

        return saveItem.getShopItemId();
    }

    @Override
    public void modifyItem(ShopItemDTO shopItemDTO) {

        Optional<ShopItem> byId = shopItemRepository.findById(shopItemDTO.getShopItemId());
        ShopItem shopItem = byId.orElseThrow();

        shopItem.change(shopItemDTO.getItemName(), shopItemDTO.getPrice(), shopItemDTO.getStockCount());

        // 첨부파일 처리
        shopItem.clearImages();

        if(shopItemDTO.getFileNames() != null){
            for (String fileName : shopItemDTO.getFileNames() ) {
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


    private static ShopItem dtoToEntity(ShopItemDTO shopItemDTO) {
        ShopItem shopItem = ShopItem.builder()
                .itemName(shopItemDTO.getItemName())
                .price(shopItemDTO.getPrice())
                .stockCount(shopItemDTO.getStockCount())
                .build();

        if(shopItemDTO.getFileNames() != null){
            shopItemDTO.getFileNames().forEach(fileName ->{
                String[] arr = fileName.split("_");
                shopItem.addImage(arr[0], arr[1]);
            });
        }

        if(!shopItemDTO.getItemOption1().isBlank()
                || !shopItemDTO.getItemOption2().isBlank() ){
            shopItem.addItemOption(shopItemDTO.getItemOption1(),shopItemDTO.getItemOption2());
        }
        return shopItem;
    }

    private static ShopItemViewDTO entityToDTO(ShopItem shopItem) {
        ShopItemViewDTO shopItemViewDTO = ShopItemViewDTO.writeShopItemViewDTOBuilder()
                .shopItemId(shopItem.getShopItemId())
                .itemName(shopItem.getItemName())
                .price(shopItem.getPrice())
                .stockCount(shopItem.getStockCount())
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
