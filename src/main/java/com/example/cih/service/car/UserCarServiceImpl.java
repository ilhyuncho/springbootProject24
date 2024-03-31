package com.example.cih.service.car;

import com.example.cih.common.fileHandler.FileHandler;
import com.example.cih.controller.fileUpload.UploadFileDTO;
import com.example.cih.domain.board.Board;
import com.example.cih.domain.car.Car;
import com.example.cih.domain.car.CarRepository;
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


@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class UserCarServiceImpl implements UserCarService {

    private final CarRepository carRepository;
    private final FileHandler fileHandler;

    @Override
    public Long register(CarSpecDTO carSpecDTO, UploadFileDTO uploadFileDTO) {

        //log.error(CarSpecDTO.toString());

        Car car = this.dtoToEntity(carSpecDTO);

        Long carId = carRepository.save(car).getCarId();

        // 파일 저장
        fileHandler.fileUpload(uploadFileDTO);

        return carId;
    }

    @Override
    public List<CarInfoDTO> readMyCarInfo(PageRequestDTO pageRequestDTO, Long UserID){

        Page<Car> result = carRepository.findByUserId(UserID, pageRequestDTO.getPageable());

        List<CarInfoDTO> listResult = new ArrayList<>();

        List<Car> content = result.getContent();
        for (Car car : content) {

            CarInfoDTO carInfoDTO = this.entityToDTO(car);
            listResult.add(carInfoDTO);
            log.error("listResult1 : " + carInfoDTO.toString());
        }

        return listResult;
    }
}
