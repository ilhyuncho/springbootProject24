package com.example.cih.service.car;

import com.example.cih.common.exception.AlreadyRegisterException;
import com.example.cih.domain.car.Car;
import com.example.cih.domain.car.CarRepository;
import com.example.cih.domain.car.Projection;
import com.example.cih.domain.reference.RefCarSample;
import com.example.cih.domain.user.User;
import com.example.cih.domain.user.UserActionType;
import com.example.cih.dto.PageRequestDTO;
import com.example.cih.dto.car.*;
import com.example.cih.service.reference.RefCarSampleService;
import com.example.cih.service.user.UserMissionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;


@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class UserCarServiceImpl implements UserCarService {

    private final CarRepository carRepository;
    private final CarService carService;
    private final UserMissionService userMissionService;
    private final RefCarSampleService refCarSampleService;

    @Override
    public List<CarViewResDTO> readMyCarList(User user){
        // 보유 Car list
        List<Car> listOwnCar = user.getOwnCars();

        return listOwnCar.stream()
                        .filter(Car::getIsActive)       // 판매 차량 제외
                        .map(UserCarServiceImpl::entityToDTO)
                        .collect(Collectors.toList());
    }

    @Override
    public CarViewResDTO readMyCarDetailInfo(User user, Long carId) {
        // 보유 Car list
        List<CarViewResDTO> listCarViewDTO = readMyCarList(user);

        // 요청된 carId 정보만 필터
        CarViewResDTO carViewResDTO = listCarViewDTO.stream()
                .filter(car -> Objects.equals(car.getCarId(), carId))
                .findFirst()
                .orElse(null);

        return carViewResDTO;
    }

    @Override
    public Long registerMyCar(User user, String carNumber) {

        // 유저의 기존 등록 차 정보 get
        List<Projection.CarSummary> userCarList = carRepository.findByUser(user);
        boolean isRegister = userCarList.stream()
                                .anyMatch(carSummary -> carSummary.getCarNumber().equals(carNumber));

        if(isRegister){
            throw new AlreadyRegisterException("이미 등록된 차량입니다.");
        }

        // 등록 하려는 차 정보 get
        RefCarSample refCarSample = refCarSampleService.findMyCar(carNumber);

        // 차 등록
        Car car = Car.builder().carNumber(refCarSample.getCarNumber())
                        .user(user)
                        .carColors(refCarSample.getCarColor())
                        .carModel(refCarSample.getCarModel())
                        .carYears(refCarSample.getCarYear())
                        .carGrade(refCarSample.getCarGrade())
                        .carKm(0L)
                        .isActive(true)
                        .build();

        // 미션 등록
        userMissionService.insertUserMission(user.getMemberId(),
                UserActionType.ACTION_REG_MY_CAR, car.getCarNumber() );

        return carRepository.save(car).getCarId();
    }
    @Override
    public void modifyMyCar(CarInfoReqDTO carInfoReqDTO) {

        Car car = carService.getCarInfo(carInfoReqDTO.getCarId());

        // 차량 스펙 업데이트
        car.changeSpec(carInfoReqDTO.getCarKm(), carInfoReqDTO.getCarYears(), carInfoReqDTO.getCarColors());

        // 차량 이미지 파일 재설정
        car.resetImages(carInfoReqDTO.getFileNames(), carInfoReqDTO.getMainImageFileName());

        carRepository.save(car);
    }

    @Override
    public void modifyMyCarKm(CarKmUpdateReqDTO carKmUpdateReqDTO) {

        Car car = carService.getCarInfo(carKmUpdateReqDTO.getCarId());

        car.changeKm(carKmUpdateReqDTO.getUpdateKmValue());
    }

    @Override
    public void deleteMyCar(Long carId) {
        Optional<Car> car = carRepository.findById(carId);
        car.ifPresent(carRepository::delete);
    }

    private static CarViewResDTO entityToDTO(Car car) {
        CarViewResDTO carViewResDTO = CarViewResDTO.writeCarViewNewDTOBuilder()
                .carId(car.getCarId())
                .userName(car.getUser().getUserName())
                .carNumber(car.getCarNumber())
                .carColors(car.getCarColors())
                .carKm(car.getCarKm())
                .carGrade(car.getCarGrade().getValue())
                .carModel(car.getCarModel())
                .carYears(car.getCarYears())
                .build();

        // 판매 진행 정보 매핑
        if(!Objects.isNull(car.getSellingCar())){
            carViewResDTO.setSellingCarId(car.getSellingCar().getSellingCarId());
            carViewResDTO.setSellingCarStatus(car.getSellingCar().getSellingCarStatus());
        }

        // 차 이미지 파일 정보 매핑
        car.getImageSet().forEach(carImage -> {
            carViewResDTO.addImage(carImage.getCarImageId(), carImage.getUuid(), carImage.getFileName(),
                    carImage.getImageOrder(), carImage.getIsMainImage());
        });

        return carViewResDTO;
    }

}
