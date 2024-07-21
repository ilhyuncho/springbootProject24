package com.example.cih.dto.order;


import com.example.cih.dto.ImageListDTO;
import com.example.cih.dto.shop.ItemOptionResDTO;
import com.example.cih.service.common.CommonUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemResDTO extends ImageListDTO {
    private Long orderId;
    private Long orderItemId;
    private String deliveryStatus;
    private int orderCount;
    private Long shopItemId;
    private String itemName;
    private int orderPrice;
    private int discountPrice;
    private LocalDate orderDate;

    @Builder.Default
    private List<ItemOptionResDTO> listItemOption = new ArrayList<>();

    public String getOptionDesc(){
        return CommonUtils.getItemOptionDesc(listItemOption);
    }
    public String getOptionIds(){
        return CommonUtils.getItemOptionIds(listItemOption);
    }


}
