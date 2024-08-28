package com.example.cih.dto.sellingCar;

import com.example.cih.domain.sellingCar.SellingCarStatus;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SellingCarRegDTO {

    private Long carId;
    private String sellType;
    private int requiredPrice;
    private Boolean isLike;
    private SellingCarStatus sellingCarStatus;   // 판매 중인 차량 취소 or 완료 전달
}
