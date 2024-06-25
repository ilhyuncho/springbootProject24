package com.example.cih.dto.order;


import com.example.cih.domain.shop.ItemOption;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    private String optionType1;
    private String optionName1;
    private String optionType2;
    private String optionName2;

    public void setOptionType1(ItemOption itemOption){
        this.optionType1 = itemOption.getType().getName();
        this.optionName1 = itemOption.getOption1();
    }
    public void setOptionType2(ItemOption itemOption){
        this.optionType2 = itemOption.getType().getName();
        this.optionName2 = itemOption.getOption1();
    }
}
