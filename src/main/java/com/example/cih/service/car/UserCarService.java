package com.example.cih.service.car;

import com.example.cih.controller.fileUpload.UploadFileDTO;
import com.example.cih.domain.car.Car;
import com.example.cih.domain.car.Projection;
import com.example.cih.dto.PageRequestDTO;
import com.example.cih.dto.car.CarInfoDTO;
import com.example.cih.dto.car.CarSpecDTO;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.stream.Collectors;


public interface UserCarService {

    Long register(String userName, CarSpecDTO carSpecDTO, UploadFileDTO uploadFileDTO);

    List<CarInfoDTO> readMyCarList(PageRequestDTO pageRequestDTO, String userName);
    List<Projection.CarSummary> readMyCarSummaryList(PageRequestDTO pageRequestDTO, String userName);


    // DTO를 엔티티로 변환하기
    default Car dtoToEntity(CarSpecDTO carSpecDTO) {

        Car car = Car.builder()
                .carId(carSpecDTO.getCarId())
                .carNumber(carSpecDTO.getCarNumber())
                .carGrade(carSpecDTO.getCarGrade())
                .carModel(carSpecDTO.getCarModel())
                .carYears(carSpecDTO.getCarYears())
                .carColors(carSpecDTO.getCarColors())
                .carKm(carSpecDTO.getCarKm())
               // .userId(40L)     // 임시
                .build();
        return car;
    }

    default CarInfoDTO entityToDTO(Car car) {
        CarInfoDTO carInfoDTO = CarInfoDTO.writeCarSpecDTOBuilder()
                .carId(car.getCarId())
                .carNumber(car.getCarNumber())
                .carColors(car.getCarColors())
                .carKm(car.getCarKm())
                .carGrade(car.getCarGrade())
                .carModel(car.getCarModel())
                .carYears(car.getCarYears())

                .modDate(car.getModDate())
                .regDate(car.getRegDate())
                .userId(car.getUser().getUserId())
               // .userId(car.getUserId())
                .build();

        return carInfoDTO;
    }
}
