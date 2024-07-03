package com.example.cih.dto.order;


import com.example.cih.dto.ImageDTO;
import com.example.cih.dto.shop.ItemOptionResDTO;
import com.example.cih.service.common.CommonUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemResDTO {
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

    @Builder.Default
    private List<ImageDTO> fileNames = new ArrayList<>();

    public void addImage(String uuid, String fileName, int imageOrder){
        ImageDTO imageDTO = ImageDTO.builder()
                .uuid(uuid)
                .fileName(fileName)
                .imageOrder(imageOrder)
                .build();
        fileNames.add(imageDTO);
    }

    public String getOptionDesc(){
        return CommonUtils.getItemOptionDesc(listItemOption);
    }


}
