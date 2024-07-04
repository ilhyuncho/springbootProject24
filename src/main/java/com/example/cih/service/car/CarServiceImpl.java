package com.example.cih.service.car;

import com.example.cih.domain.car.Car;
import com.example.cih.domain.car.CarRepository;
import com.example.cih.dto.car.CarViewDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;
import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;

    @Override
    public CarViewDTO readOne(Long carId){

        Optional<Car> result = carRepository.findById(carId);

        Car car = result.orElseThrow();

        CarViewDTO carViewDTO = entityToDTO(car);

        return carViewDTO;
    }

    private static CarViewDTO entityToDTO(Car car) {
        CarViewDTO carViewDTO = CarViewDTO.writeCarViewDTOBuilder()
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
                .userName(car.getUser().getUserName())
                .build();

        // 경매 정보 매핑
        if(!Objects.isNull(car.getSellingCar())){
            carViewDTO.setSellingCarId(car.getSellingCar().getSellingCarId());
            carViewDTO.setSellingCarStatus(car.getSellingCar().getSellingCarStatus());
        }

        // 차 이미지 파일 정보 매핑
        car.getImageSet().forEach(carImage -> {
            //  log.error(carImage.getUuid()+ carImage.getFileName()+ carImage.getImageOrder());
            carViewDTO.addImage(carImage.getUuid(), carImage.getFileName(), carImage.getImageOrder());
        });

        return carViewDTO;
    }

}
