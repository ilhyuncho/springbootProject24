package com.example.cih.dto.sellingCar;
import com.example.cih.domain.sellingCar.SellingCarStatus;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SellingCarViewDTO {
    private Long carId;
    private SellingCarStatus sellingCarStatus;
    private int requiredPrice;
    private LocalDateTime expiredDate;
}
