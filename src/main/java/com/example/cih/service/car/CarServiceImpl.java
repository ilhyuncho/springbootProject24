package com.example.cih.service.car;

import com.example.cih.common.exception.OwnerCarNotFoundException;
import com.example.cih.domain.car.Car;
import com.example.cih.domain.car.CarRepository;
import com.example.cih.dto.car.CarViewResDTO;
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
    public Car getCarInfo(Long carId) {
        return carRepository.findById(carId)
                .orElseThrow(() -> new OwnerCarNotFoundException("차 정보가 존재하지않습니다"));
    }

    @Override
    public CarViewResDTO readOne(Long carId){

        Car car = getCarInfo(carId);

        return entityToDTO(car);
    }

    private static CarViewResDTO entityToDTO(Car car) {
        CarViewResDTO carViewResDTO = CarViewResDTO.writeCarViewNewDTOBuilder()
                .carId(car.getCarId())
                .carNumber(car.getCarNumber())
                .carColors(car.getCarColors())
                .carKm(car.getCarKm())
                .carGrade(car.getCarGrade().getValue())
                .carModel(car.getCarModel())
                .carYears(car.getCarYears())
                .userName(car.getUser().getUserName())
                .memberId(car.getUser().getMemberId())
                .build();

        // 판매 진행 상황 매핑
        if(!Objects.isNull(car.getSellingCar())){
            carViewResDTO.setSellingCarId(car.getSellingCar().getSellingCarId());
            carViewResDTO.setSellingCarStatus(car.getSellingCar().getSellingCarStatus());
            carViewResDTO.setSellType(car.getSellingCar().getSellType());
        }

        // 차 이미지 파일 정보 매핑
        car.getImageSet().forEach(carImage -> {
            carViewResDTO.addImage(carImage.getCarImageId(), carImage.getUuid(), carImage.getFileName(),
                    carImage.getImageOrder(), carImage.getIsMainImage());
        });

        return carViewResDTO;
    }

}
