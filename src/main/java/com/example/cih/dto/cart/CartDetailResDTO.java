package com.example.cih.dto.cart;


import com.example.cih.domain.shop.ItemOption;
import com.example.cih.dto.ImageDTO;
import com.example.cih.dto.shop.ItemOptionResDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
@Builder
public class CartDetailResDTO {
    private Long cartId;
    private Long shopItemId;
    private String itemName;
    private Integer itemCount;
    private Integer itemPrice;
    private Integer discountPrice;

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

        ItemOptionResDTO itemOption1 = listItemOption.get(0);
        String desc = itemOption1.getOptionType() + ": " + itemOption1.getOptionName();

        if(listItemOption.size() > 1){
            ItemOptionResDTO itemOption2 = listItemOption.get(1);
            desc += ", " + itemOption2.getOptionType() + ": " + itemOption2.getOptionName();
        }
        return desc;
    }

}
