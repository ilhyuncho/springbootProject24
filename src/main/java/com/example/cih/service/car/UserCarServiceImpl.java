package com.example.cih.service.car;

import com.example.cih.domain.board.Board;
import com.example.cih.domain.car.Car;
import com.example.cih.domain.car.CarRepository;
import com.example.cih.dto.PageRequestDTO;
import com.example.cih.dto.car.CarRegisterDTO;
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
    @Override
    public Long register(CarRegisterDTO carRegisterDTO) {

        //log.error(carRegisterDTO.toString());

        Car car = this.dtoToEntity(carRegisterDTO);

        Long carId = carRepository.save(car).getCarId();

        return carId;
    }

    @Override
    public List<CarRegisterDTO> readMyCarInfo(PageRequestDTO pageRequestDTO, Long UserID){

        Page<Car> result = carRepository.findByUserId(UserID, pageRequestDTO.getPageable());

        List<CarRegisterDTO> listResult = new ArrayList<>();

        List<Car> content = result.getContent();
        for (Car car : content) {
            CarRegisterDTO carRegisterDTO = this.entityToDTO(car);
            listResult.add(carRegisterDTO);
            log.error("listResult : " + carRegisterDTO.toString());
        }

        return listResult;
    }
}
