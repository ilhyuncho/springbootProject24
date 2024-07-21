package com.example.cih.dto.shop;

import com.example.cih.domain.shop.ItemSellingStatus;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ShopItemExtandDTO extends ShopItemResDTO {
    private Integer stockCount;         // 재고수량
    private Integer purchaseCount;      // 구매수량
    private Integer membershipPercent;
    private Boolean isEventTarget;

    @Builder.Default
    private List<ItemOptionDTO> listOptionType= new ArrayList<>();

    public ItemOptionDTO getOptionType(Integer index){
        return listOptionType.get(index);
    }

    public ItemSellingStatus getSellingStatus(){
        return this.stockCount <= 0 ? ItemSellingStatus.STATUS_SOLDOUT : ItemSellingStatus.STATUS_SELLING;
    }
}
