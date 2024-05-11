package com.example.cih.dto.sellingCar;
import com.example.cih.domain.car.Car;
import com.example.cih.domain.sellingCar.SellingCarStatus;
import com.example.cih.dto.car.CarImageDTO;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SellingCarViewDTO {
    private Long carId;
    private String carNumber;
    private SellingCarStatus sellingCarStatus;
    private int requiredPrice;
    private Long sellingCarId;
    private LocalDateTime expiredDate;

    @Builder.Default
    private List<CarImageDTO> fileNames = new ArrayList<>();

    public void addImage(String uuid, String fileName, int imageOrder){
        CarImageDTO carImage = CarImageDTO.builder()
                .uuid(uuid)
                .fileName(fileName)
                .imageOrder(imageOrder)
                .build();
        fileNames.add(carImage);
    }

}
