package com.example.cih.service.car;

import com.example.cih.common.exception.UserNotFoundException;
import com.example.cih.common.fileHandler.FileHandler;
import com.example.cih.controller.fileUpload.UploadFileDTO;
import com.example.cih.domain.car.Car;
import com.example.cih.domain.car.CarRepository;
import com.example.cih.domain.car.Projection;
import com.example.cih.domain.user.User;
import com.example.cih.domain.user.UserRepository;
import com.example.cih.dto.PageRequestDTO;
import com.example.cih.dto.car.CarInfoDTO;
import com.example.cih.dto.car.CarViewDTO;
import com.example.cih.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class UserCarServiceImpl implements UserCarService {

    private final CarRepository carRepository;
    private final UserRepository userRepository;
    private final FileHandler fileHandler;

    private final UserService userService;

    @Override
    public Long register(String userName, CarInfoDTO carInfoDTO, UploadFileDTO uploadFileDTO) {

        // 고객 정보 get
        User user = userService.findUser(userName);

        Car car = dtoToEntity(carInfoDTO, user);

        // 파일 저장
        // fileHandler.fileUpload(uploadFileDTO);

        return carRepository.save(car).getCarId();
    }

    @Override
    public List<CarViewDTO> readMyCarList(PageRequestDTO pageRequestDTO, String userName){

        // 고객 정보 get
        User user = userService.findUser(userName);

        // 보유 차량 get
        // 1차 작업
        //        Page<Car> result = carRepository.findByUser(userInfo, pageRequestDTO.getPageable());
        //
        //        List<Car> content = result.getContent();
        //        for (Car car : content) {
        //
        //            CarInfoDTO carInfoDTO = this.entityToDTO(car);
        //            listResult.add(carInfoDTO);
        //           // log.error("listResult1 : " + carInfoDTO.toString());
        //        }

        // 2차 작업
        //        List<CarInfoDTO> listResult = new ArrayList<>();
        //        for (Car car : userInfo.getOwnCars()) {
        //            CarInfoDTO carInfoDTO = this.entityToDTO(car);
        //            listResult.add(carInfoDTO);
        //            log.error("listResult1 : " + carInfoDTO.toString());
        //        }

        // 3차 작업
        List<CarViewDTO> listResult = user.getOwnCars().stream()
                .map(UserCarServiceImpl::entityToDTO).collect(Collectors.toList());

        return listResult;
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
        CarViewDTO carInfoDTO = CarViewDTO.writeCarSpecDTOBuilder()
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
                .build();

        // 차 이미지 파일 정보 매핑
        car.getImageSet().forEach(carImage -> {

            carInfoDTO.addImage(carImage.getUuid(), carImage.getFileName(), carImage.getImageOrder());
        });

        return carInfoDTO;
    }


}
