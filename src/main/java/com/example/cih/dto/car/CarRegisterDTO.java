package com.example.cih.dto.car;

import com.example.cih.domain.car.CarSize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CarRegisterDTO {

    private Long    carId;
    private String  carNumber;
    private CarSize carGrade;
    private String  carModel;
    private int     carYears;
    private String  carColors;
    private Long    carKm;

}
