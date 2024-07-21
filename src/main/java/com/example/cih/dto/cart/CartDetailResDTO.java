package com.example.cih.dto.cart;


import com.example.cih.dto.ImageListDTO;
import com.example.cih.dto.shop.ItemOptionResDTO;
import com.example.cih.service.common.CommonUtils;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
@SuperBuilder
public class CartDetailResDTO extends ImageListDTO {
    private Long cartId;
    private Long shopItemId;
    private String itemName;
    private Integer itemCount;
    private Integer itemPrice;
    private Integer discountPrice;
    private Boolean isFreeDelivery;

    @Builder.Default
    private List<ItemOptionResDTO> listItemOption = new ArrayList<>();

    public String getOptionDesc(){return CommonUtils.getItemOptionDesc(listItemOption);
    }
    public String getOptionIds(){return CommonUtils.getItemOptionIds(listItemOption);
    }

}
