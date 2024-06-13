package com.example.cih.domain.reference;


import com.example.cih.domain.car.CarSize;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CarInfo {
    private String carModel;
    private CarSize carGrade;
    private String carOption;
    private String company;
    private String companyNation;
    private Integer modelYear;
}
