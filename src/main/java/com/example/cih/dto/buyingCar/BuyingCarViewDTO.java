package com.example.cih.dto.buyingCar;
import com.example.cih.domain.buyingCar.BuyCarStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BuyingCarViewDTO {

    private String userName;
    private String memberId;
    private int proposalPrice;
    private BuyCarStatus buyCarStatus;
    private String carNumber;
    private String carModel;
    private Long carId;


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime registerTime;

}
