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
public class OrderTemporaryResDTO extends ImageListDTO {
    private Long orderTemporaryId;
    private Long shopItemId;
    private String itemName;
    private int orderPrice;
    private int discountPrice;
    private int orderCount;
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
