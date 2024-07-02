package com.example.cih.dto.sellingCar;
import com.example.cih.domain.buyingCar.BuyCarStatus;
import com.example.cih.domain.sellingCar.SellingCarStatus;
import com.example.cih.dto.ImageDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
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
    private String carModel;
    private int carYears;
    private SellingCarStatus sellingCarStatus;
    private BuyCarStatus buyCarStatus;
    private int requiredPrice;
    private Long sellingCarId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime expiredDate;

    @Builder.Default
    private List<ImageDTO> fileNames = new ArrayList<>();

    public void addImage(String uuid, String fileName, int imageOrder){
        ImageDTO imageDTO = ImageDTO.builder()
                .uuid(uuid)
                .fileName(fileName)
                .imageOrder(imageOrder)
                .build();
        fileNames.add(imageDTO);
    }

}
