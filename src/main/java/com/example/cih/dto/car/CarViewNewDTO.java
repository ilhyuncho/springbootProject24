package com.example.cih.dto.car;

import com.example.cih.domain.sellingCar.SellingCarStatus;
import com.example.cih.dto.ImageDTO;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CarViewNewDTO{        // 내차 정보 로딩 시

    private Long carId;
    private String userName;

    @Pattern(regexp = "[0-9]{2,3}[가-힣][0-9]{4}$")
    private String  carNumber;

    private String carGrade;

    @NotEmpty
    private String  carModel;

    @NotNull
    private int     carYears;

    @NotEmpty
    private String  carColors;

    @NotNull
    private Long    carKm;

    private List<ImageDTO> fileNames = new ArrayList<>();

    private Long sellingCarId;
    private SellingCarStatus sellingCarStatus;

    @Builder(builderMethodName = "writeCarViewNewDTOBuilder")
    public CarViewNewDTO(Long carId, String userName, String carNumber, String carGrade, String carModel, int carYears,
                         String carColors, Long carKm) {
        this.carId = carId;
        this.userName = userName;
        this.carNumber = carNumber;
        this.carGrade = carGrade;
        this.carModel = carModel;
        this.carYears = carYears;
        this.carColors = carColors;
        this.carKm = carKm;
    }

    public void addImage(String uuid, String fileName, int imageOrder){
        ImageDTO imageDTO = ImageDTO.builder()
                .uuid(uuid)
                .fileName(fileName)
                .imageOrder(imageOrder)
                .build();
        fileNames.add(imageDTO);
    }
}