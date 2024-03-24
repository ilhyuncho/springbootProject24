package com.example.cih.service.car;

import com.example.cih.domain.board.Board;
import com.example.cih.domain.car.Car;
import com.example.cih.domain.car.CarRepository;
import com.example.cih.dto.car.CarRegisterDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


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
}
