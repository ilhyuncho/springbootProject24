package com.example.cih.dto.car;

import com.example.cih.domain.car.CarSize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CarDTO {

    private Long carId;

    @NotEmpty
    private String carNumber;
    @NotEmpty
    private CarSize carGrade;
    @NotEmpty
    private String carModel;
    @NotEmpty
    private int carYears;
    @NotEmpty
    private String carColors;
    @NotEmpty
    private Long carKm;
    @NotEmpty
    private Long userId;

    private LocalDateTime regDate;
    private LocalDateTime modDate;
}
