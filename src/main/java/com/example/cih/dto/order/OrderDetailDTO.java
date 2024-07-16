package com.example.cih.dto.order;


import lombok.*;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class OrderDetailDTO{
    private Long cartId;
    private Long itemId;
    private Integer itemCount;
    private Integer itemPrice;
    private Integer discountPrice;
    private String itemOptionIds;           // 선택한 옵션 ids ( 예) 3-3 )

    public Long getOptionId(int index){
        String[] split = itemOptionIds.split(",");
        return Long.parseLong(split[index]);
    }

}
