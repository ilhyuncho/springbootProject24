package com.example.cih.service.car;

import com.example.cih.common.exception.OwnerCarNotFoundException;
import com.example.cih.domain.car.Car;
import com.example.cih.domain.car.CarRepository;
import com.example.cih.dto.car.CarViewNewDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;

    @Override
    public CarViewNewDTO readOne(Long carId){

        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new OwnerCarNotFoundException("차 정보가 존재하지않습니다"));

        CarViewNewDTO carViewDTO = entityToDTO(car);

        return carViewDTO;
    }

    private static CarViewNewDTO entityToDTO(Car car) {
        CarViewNewDTO carViewNewDTO = CarViewNewDTO.writeCarViewNewDTOBuilder()
                .carId(car.getCarId())
                .carNumber(car.getCarNumber())
                .carColors(car.getCarColors())
                .carKm(car.getCarKm())
                .carGrade(car.getCarGrade().getValue())
                .carModel(car.getCarModel())
                .carYears(car.getCarYears())
                .userName(car.getUser().getUserName())
                .build();

        // 판매 진행 상황 매핑
        if(!Objects.isNull(car.getSellingCar())){
            carViewNewDTO.setSellingCarId(car.getSellingCar().getSellingCarId());
            carViewNewDTO.setSellingCarStatus(car.getSellingCar().getSellingCarStatus());
        }

        // 차 이미지 파일 정보 매핑
        car.getImageSet().forEach(carImage -> {
            carViewNewDTO.addImage(carImage.getUuid(), carImage.getFileName(), carImage.getImageOrder());
        });

        return carViewNewDTO;
    }

}
