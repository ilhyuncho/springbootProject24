package com.example.cih.service.car;

import com.example.cih.domain.board.Board;
import com.example.cih.domain.car.Car;
import com.example.cih.dto.PageRequestDTO;
import com.example.cih.dto.car.CarRegisterDTO;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.stream.Collectors;


public interface UserCarService {

    Long register(CarRegisterDTO carRegisterDTO);

    List<CarRegisterDTO> readMyCarInfo(PageRequestDTO pageRequestDTO, Long UserID);

    // DTO를 엔티티로 변환하기
    default Car dtoToEntity(CarRegisterDTO carRegisterDTO) {

        Car car = Car.builder()
                .carId(carRegisterDTO.getCarId())
                .carNumber(carRegisterDTO.getCarNumber())
                .carGrade(carRegisterDTO.getCarGrade())
                .carModel(carRegisterDTO.getCarModel())
                .carYears(carRegisterDTO.getCarYears())
                .carColors(carRegisterDTO.getCarColors())
                .carKm(carRegisterDTO.getCarKm())
                .userId(40L)     // 임시
                .build();
        return car;
    }

    default CarRegisterDTO entityToDTO(Car car) {
        CarRegisterDTO carRegisterDTO = CarRegisterDTO.builder()
                .carId(car.getCarId())
                .carNumber(car.getCarNumber())
                .carColors(car.getCarColors())
                .carKm(car.getCarKm())
                .carGrade(car.getCarGrade())
                .carModel(car.getCarModel())
                .carYears(car.getCarYears())
                .build();

        return carRegisterDTO;
    }
}
