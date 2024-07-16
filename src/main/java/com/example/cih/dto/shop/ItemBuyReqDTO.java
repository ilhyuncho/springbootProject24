package com.example.cih.dto.shop;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemBuyReqDTO {
    private Long cartId;
    private Long shopItemId;
    private String itemName;
    private Integer itemCount;
    private Integer itemPrice;
    private String itemOptionIds;           // 선택한 옵션 ids ( 예) 3-3 )

    public Long getOptionId(int index){
        String[] split = itemOptionIds.split(",");
        return Long.parseLong(split[index]);
    }

}
