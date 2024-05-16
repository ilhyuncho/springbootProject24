package com.example.cih.dto.buyingCar;
import com.example.cih.domain.buyingCar.BuyResult;
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
    private BuyResult buyResult;
    private String carNumber;
    private String carModel;


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime registerDate;

}
