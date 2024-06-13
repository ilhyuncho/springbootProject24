package com.example.cih.service.car;

import com.example.cih.common.exception.AlreadyRegisterException;
import com.example.cih.domain.car.Car;
import com.example.cih.domain.car.CarRepository;
import com.example.cih.domain.car.Projection;
import com.example.cih.domain.reference.RefCarSample;
import com.example.cih.domain.user.User;
import com.example.cih.domain.user.UserActionType;
import com.example.cih.dto.PageRequestDTO;
import com.example.cih.dto.car.CarInfoDTO;
import com.example.cih.dto.car.CarKmUpdateDTO;
import com.example.cih.dto.car.CarViewDTO;
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
    public CarViewDTO readMyCarDetailInfo(String userName, Long carId) {
        // 고객 정보 get
        User user = userService.findUser(userName);

        // 전체 보유 Car list
        List<Car> ownCarList = user.getOwnCars();

        List<CarViewDTO> carViewDTOList = ownCarList.stream().
                map(UserCarServiceImpl::entityToDTO).collect(Collectors.toList());

        // 요청된 carId 정보만 필터
        CarViewDTO carViewDTO = carViewDTOList.stream()
                .filter(car -> Objects.equals(car.getCarId(), carId))
                .findFirst()
                .orElse(null);

        log.error("carViewDTO : " + carViewDTO);

        return carViewDTO;
    }

    @Override
    public List<CarViewDTO> readMyCarList(PageRequestDTO pageRequestDTO, String userName){
        // 고객 정보 get
        User user = userService.findUser(userName);

        // 전체 보유 Car list
        List<Car> ownCarList = user.getOwnCars();

        List<CarViewDTO> carViewDTOList = ownCarList.stream().
                map(UserCarServiceImpl::entityToDTO).collect(Collectors.toList());

        // 대표 이미지만 필터링 ( ImageOrder = 0 )
        for (CarViewDTO car : carViewDTOList) {
            car.getFileNames().stream()
                    .filter(carImage -> carImage.getImageOrder() != 0)
                    .collect(Collectors.toList())
                    .forEach(x-> car.getFileNames().remove(x));
        }

        return carViewDTOList;
    }

    @Override
    public List<Projection.CarSummary> readMyCarSummaryList(PageRequestDTO pageRequestDTO, String userName){

        // 고객 정보 get
        User user = userService.findUser(userName);

        // 보유 차량 get
        List<Projection.CarSummary> carSummaryList = carRepository.findByUser(user);

        for (Projection.CarSummary carSummary : carSummaryList) {
            log.error(carSummary.getCarInfo());
            log.error(carSummary.getCarNumber());
        }

        return carSummaryList;

    }

    @Override
    public void modifyMyCar(CarInfoDTO carInfoDTO) {

        Optional<Car> byId = carRepository.findById(carInfoDTO.getCarId());
        Car car = byId.orElseThrow();

        car.change(carInfoDTO.getCarNumber(), carInfoDTO.getCarKm(), carInfoDTO.getCarGrade(),
                     carInfoDTO.getCarModel(), carInfoDTO.getCarYears(), carInfoDTO.getCarColors());

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

    // DTO를 엔티티로 변환하기
    private static Car dtoToEntity(CarInfoDTO carSpecDTO, User user) {
        Car car = Car.writeWithUserBuilder()
                .carColors(carSpecDTO.getCarColors())
                .carYears(carSpecDTO.getCarYears())
                .carModel(carSpecDTO.getCarModel())
                .carKm(carSpecDTO.getCarKm())
                .carNumber(carSpecDTO.getCarNumber())
                .carGrade(carSpecDTO.getCarGrade())
                .user(user)
                .build();

        if(carSpecDTO.getFileNames() != null){
            carSpecDTO.getFileNames().forEach(fileName ->{
                String[] arr = fileName.split("_");
                car.addImage(arr[0], arr[1]);
            });
        }
        return car;
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
