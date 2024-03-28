package com.example.cih.dto.car;

import com.example.cih.domain.car.CarSize;
import lombok.*;

import javax.validation.constraints.Pattern;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CarRegisterDTO {

    private Long    carId;
    // 차량 번호 정규식
    @Pattern(regexp = "[0-9]{2,3}[가-힣][0-9]{4}$")
    private String  carNumber;
    private CarSize carGrade;
    private String  carModel;
    private int     carYears;
    private String  carColors;
    private Long    carKm;

}
