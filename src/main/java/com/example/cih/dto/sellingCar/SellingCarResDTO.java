package com.example.cih.dto.sellingCar;
import com.example.cih.domain.buyingCar.BuyCarStatus;
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
    private String sellingCarStatusText;
    private String sellTypeText;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime expiredDate;
}
