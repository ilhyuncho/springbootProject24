package com.example.cih.dto.sellingCar;
import com.example.cih.domain.buyingCar.BuyCarStatus;
import com.example.cih.domain.sellingCar.SellType;
import com.example.cih.domain.sellingCar.SellingCarStatus;
import com.example.cih.dto.ImageListDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class SellingCarResDTO extends ImageListDTO {
    private Long carId;
    private String carNumber;
    private String carModel;
    private Integer carYears;
    private Boolean isLike;
    private Integer requiredPrice;
    private Long sellingCarId;
    private Integer viewCount;

    private BuyCarStatus buyCarStatus;
    private SellingCarStatus sellingCarStatus;

    private SellType sellType;
    private Integer proposalPrice;          // 경매 응찰한 가격

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime expiredDate;
}
