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
import com.example.cih.service.user.UserService;
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

    private final UserService userService;
    private final UserMissionService userMissionService;
    private final RefCarSampleService refCarSampleService;

    @Override
    public List<CarViewNewDTO> readMyCarList(PageRequestDTO pageRequestDTO, String userName){
        // 고객 정보 get
        User user = userService.findUser(userName);

        // 전체 보유 Car list
        List<Car> ownCarList = user.getOwnCars();

        List<CarViewNewDTO> carViewDTOList = ownCarList.stream().
                map(UserCarServiceImpl::entityToDTO).collect(Collectors.toList());

        // 대표 이미지만 필터링 ( ImageOrder = 0 )
        for (CarViewNewDTO car : carViewDTOList) {
            car.getFileNames().stream()
                    .filter(carImage -> carImage.getImageOrder() != 0)
                    .collect(Collectors.toList())
                    .forEach(x-> car.getFileNames().remove(x));
        }

        return carViewDTOList;
    }

    @Override
    public CarViewNewDTO readMyCarDetailInfo(String userName, Long carId) {
        // 고객 정보 get
        User user = userService.findUser(userName);

        // 전체 보유 Car list
        List<Car> ownCarList = user.getOwnCars();

        List<CarViewNewDTO> carViewDTOList = ownCarList.stream().
                map(UserCarServiceImpl::entityToDTO).collect(Collectors.toList());

        // 요청된 carId 정보만 필터
        CarViewNewDTO carViewDTO = carViewDTOList.stream()
                .filter(car -> Objects.equals(car.getCarId(), carId))
                .findFirst()
                .orElse(null);

        log.error("carViewDTO : " + carViewDTO);

        return carViewDTO;
    }

    @Override
    public Long register(String userName, String carNumber) {

        // 유저의 기존 등록 차 정보 get
        User user = userService.findUser(userName);
        List<Projection.CarSummary> userCarList = carRepository.findByUser(user);
        boolean isRegister = userCarList.stream().anyMatch(carSummary -> carSummary.getCarNumber().equals(carNumber));
        if(isRegister){
            throw new AlreadyRegisterException("이미 등록된 차량입니다.");
           // return 0L;
        }

        // 등록 하려는 차 정보 get
        RefCarSample refCarSample = refCarSampleService.findMyCar(carNumber);

        // 차 등록
        Car car = Car.builder().carNumber(refCarSample.getCarNumber())
                        .carColors(refCarSample.getCarColor())
                        .carModel(refCarSample.getCarModel())
                        .carYears(refCarSample.getCarYear())
                        .carGrade(refCarSample.getCarGrade())
                        .carKm(0L)
                        .user(user)
                .build();

        userMissionService.insertUserMission(userName, UserActionType.ACTION_REG_MY_CAR, car.getCarNumber() );

        return carRepository.save(car).getCarId();
    }
    @Override
    public void modifyMyCar(CarInfoNewDTO carInfoDTO) {

        Optional<Car> byId = carRepository.findById(carInfoDTO.getCarId());
        Car car = byId.orElseThrow();

        car.change(carInfoDTO.getCarKm(), carInfoDTO.getCarYears(), carInfoDTO.getCarColors());

        // 첨부파일 처리
        car.clearImages();

        if(carInfoDTO.getFileNames() != null){
            for (String fileName : carInfoDTO.getFileNames() ) {
                String[] index = fileName.split("_");
                car.addImage(index[0], index[1]);
            }
        }

        carRepository.save(car);
    }

    @Override
    public void modifyMyCarKm(CarKmUpdateDTO carKmUpdateDTO) {
        Optional<Car> byId = carRepository.findById(carKmUpdateDTO.getCarId());
        Car car = byId.orElseThrow();

        car.changeKm(carKmUpdateDTO.getUpdateKmValue());
    }

    @Override
    public void deleteMyCar(Long carId) {
        carRepository.deleteById(carId);
    }

    private static CarViewNewDTO entityToDTO(Car car) {
        CarViewNewDTO carViewDTO = CarViewNewDTO.writeCarViewNewDTOBuilder()
                .carId(car.getCarId())
                .carNumber(car.getCarNumber())
                .carColors(car.getCarColors())
                .carKm(car.getCarKm())
                .carGrade(car.getCarGrade().getValue())
                .carModel(car.getCarModel())
                .carYears(car.getCarYears())
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
