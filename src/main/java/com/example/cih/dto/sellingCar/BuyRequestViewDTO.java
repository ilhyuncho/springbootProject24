package com.example.cih.dto.sellingCar;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BuyRequestViewDTO {

    private int proposalPrice;
    private LocalDateTime registerDate;
}
