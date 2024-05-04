package com.example.cih.service.shop;

import com.example.cih.common.exception.ItemNotFoundException;
import com.example.cih.domain.shop.ShopItem;
import com.example.cih.domain.shop.ShopItemRepository;
import com.example.cih.dto.shop.ShopItemDTO;
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
    public ShopItemDTO findOne(Long shopItemId) {

        ShopItem shopItem = shopItemRepository.findById(shopItemId)
                .orElseThrow(() -> new ItemNotFoundException("해당 상품 정보가 존재하지않습니다"));

        ShopItemDTO shopItemDTO = entityToDTO(shopItem);

        return shopItemDTO;

    }

    @Override
    public List<ShopItemDTO> getAllItems() {

        List<ShopItem> shopItemList = shopItemRepository.findAll();

        List<ShopItemDTO> shopItemDTOList = shopItemList.stream().
                map(shopItem -> modelMapper.map(shopItem, ShopItemDTO.class)).
                collect(Collectors.toList());

        return shopItemDTOList;
    }

    @Override
    public Long registerItem(ShopItemDTO shopItemDTO) {

        ShopItem shopItem = dtoToEntity(shopItemDTO);

        ShopItem saveItem = shopItemRepository.save(shopItem);

        return saveItem.getShopItemId();
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

    private static ShopItemDTO entityToDTO(ShopItem shopItem) {
        ShopItemDTO shopItemDTO = ShopItemDTO.builder()
                .shopItemId(shopItem.getShopItemId())
                .itemName(shopItem.getItemName())
                .price(shopItem.getPrice())
                .stockCount(shopItem.getStockCount())
                .build();

        // 임시로... cih
        shopItem.getItemOptionSet().forEach(itemOption -> {
            //  log.error(carImage.getUuid()+ carImage.getFileName()+ carImage.getImageOrder());
            shopItemDTO.setItemOption1(itemOption.getOption1());
            shopItemDTO.setItemOption2(itemOption.getOption2());
        });

        return shopItemDTO;
    }

}
