package com.example.cih.dto.buyingCar;
import com.example.cih.domain.buyingCar.BuyCarStatus;
import com.example.cih.dto.PageRequestDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BuyingCarViewDTO {

    private String userName;
    private int proposalPrice;
    private BuyCarStatus buyCarStatus;
    private String carNumber;
    private String carModel;
    private Long carId;


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime registerDate;

}
