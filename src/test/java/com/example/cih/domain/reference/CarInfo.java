package com.example.cih.domain.reference;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CarInfo {
    private String carModel;
    private String carGrade;
    private String carOption;
    private String company;
    private String companyNation;
    private Integer modelYear;
}
