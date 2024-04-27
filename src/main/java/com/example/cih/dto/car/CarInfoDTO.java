package com.example.cih.dto.car;

import com.example.cih.domain.car.CarSize;
import com.example.cih.service.car.CarService;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class CarInfoDTO extends CarSpecDTO {

    private Long userId;

    private LocalDateTime regDate;
    private LocalDateTime modDate;

    private List<String> fileNames;

    // 상속시 @Builder를 자식 클래스에 따로 지정 시
    @Builder(builderMethodName = "writeCarSpecDTOBuilder")
    public CarInfoDTO(Long carId, String carNumber, CarSize carGrade, String carModel, int carYears,
                      String carColors, Long carKm, Long userId ,
                      LocalDateTime regDate, LocalDateTime modDate, List<String> fileNames) {

        super(carId, carNumber, carGrade, carModel, carYears, carColors, carKm);

        this.userId = userId;
        this.regDate = regDate;
        this.modDate = modDate;

        this.fileNames = fileNames; // 확인 필요
    }
}