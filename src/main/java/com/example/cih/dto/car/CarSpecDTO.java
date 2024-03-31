package com.example.cih.dto.car;

import com.example.cih.common.validator.CarGradeVali;
import com.example.cih.domain.car.CarSize;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CarSpecDTO {

    private Long    carId;
    // 차량 번호 정규식
    @Pattern(regexp = "[0-9]{2,3}[가-힣][0-9]{4}$")
    private String  carNumber;

    @CarGradeVali(enumClass = CarSize.class)
    private CarSize carGrade;

    @NotEmpty
    private String  carModel;

    @NotNull
    private int     carYears;

    @NotEmpty
    private String  carColors;

    @NotNull
    private Long    carKm;

}
