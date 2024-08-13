package com.example.cih.dto.car;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CarInfoReqDTO {

    private Long    carId;
    @NotNull
    private int     carYears;
    @NotEmpty
    private String  carColors;
    @NotNull
    private Long    carKm;

    private String mainImageFileName;
    private List<String> fileNames = new ArrayList<>();
}