package com.example.cih.service.car;

import com.example.cih.common.fileHandler.FileHandler;
import com.example.cih.controller.fileUpload.UploadFileDTO;
import com.example.cih.domain.car.Car;
import com.example.cih.domain.car.CarRepository;
import com.example.cih.domain.user.User;
import com.example.cih.domain.user.UserRepository;
import com.example.cih.dto.PageRequestDTO;
import com.example.cih.dto.car.CarInfoDTO;
import com.example.cih.dto.car.CarSpecDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class UserCarServiceImpl implements UserCarService {

    private final CarRepository carRepository;
    private final UserRepository userRepository;
    private final FileHandler fileHandler;

    @Override
    public Long register(String userName, CarSpecDTO carSpecDTO, UploadFileDTO uploadFileDTO) {

        log.error(carSpecDTO.toString());

        // 고객 정보 get
        Optional<User> user = userRepository.findByUserName(userName);
        User userInfo = user.orElseThrow();


        // Car car = this.dtoToEntity(carSpecDTO);
        Car car = Car.writeWithUserBuilder()
                .carColors(carSpecDTO.getCarColors())
                .carYears(carSpecDTO.getCarYears())
                .carModel(carSpecDTO.getCarModel())
                .carKm(carSpecDTO.getCarKm())
                .carNumber(carSpecDTO.getCarNumber())
                .carGrade(carSpecDTO.getCarGrade())
                .user(userInfo).build();

        Long carId = carRepository.save(car).getCarId();

        // 파일 저장
        // fileHandler.fileUpload(uploadFileDTO);

        return carId;
    }

    @Override
    public List<CarInfoDTO> readMyCarList(PageRequestDTO pageRequestDTO, String UserName){

        List<CarInfoDTO> listResult = new ArrayList<>();

        // 고객 정보 get
        Optional<User> user = userRepository.findByUserName(UserName);
        User userInfo = user.orElseThrow();

        // 보유 차량 get
        Page<Car> result = carRepository.findByUser(userInfo, pageRequestDTO.getPageable());

        List<Car> content = result.getContent();
        for (Car car : content) {

            CarInfoDTO carInfoDTO = this.entityToDTO(car);
            listResult.add(carInfoDTO);
            log.error("listResult1 : " + carInfoDTO.toString());
        }

        return listResult;
    }



}
