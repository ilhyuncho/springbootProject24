package com.example.cih.dto.car;

import com.example.cih.domain.car.CarImage;
import com.example.cih.domain.car.CarSize;
import com.example.cih.domain.sellingCar.SellingCarStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class CarViewDTO extends CarSpecDTO {

    private Long userId;

    private LocalDateTime regDate;
    private LocalDateTime modDate;
    private List<CarImageDTO> fileNames = new ArrayList<>();

    private Long sellingCarId;
    private SellingCarStatus sellingCarStatus;

    @Builder(builderMethodName = "writeCarViewDTOBuilder")
    public CarViewDTO(Long carId, String carNumber, CarSize carGrade, String carModel, int carYears,
                      String carColors, Long carKm, Long userId,
                      LocalDateTime regDate, LocalDateTime modDate) {

        super(carId, carNumber, carGrade, carModel, carYears, carColors, carKm);

        this.userId = userId;
        this.regDate = regDate;
        this.modDate = modDate;
       // this.fileNames = fileNames; // 확인 필요
    }

    public void addImage(String uuid, String fileName, int imageOrder){
        CarImageDTO carImage = CarImageDTO.builder()
                .uuid(uuid)
                .fileName(fileName)
                .imageOrder(imageOrder)
                .build();
        fileNames.add(carImage);
    }
}