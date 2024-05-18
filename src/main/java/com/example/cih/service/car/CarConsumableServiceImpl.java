package com.example.cih.service.car;

import com.example.cih.common.exception.OwnerCarNotFoundException;
import com.example.cih.domain.car.Car;
import com.example.cih.domain.car.CarConsumable;
import com.example.cih.domain.car.CarConsumableRepository;
import com.example.cih.domain.car.CarRepository;
import com.example.cih.dto.car.CarConsumableDTO;
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
public class CarConsumableServiceImpl implements CarConsumableService {

    private final CarRepository carRepository;
    private final CarConsumableRepository carConsumableRepository;

    @Override
    public List<CarConsumableDTO> readOne(Long carId){

        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new OwnerCarNotFoundException("차 정보가 존재하지않습니다"));

        List<CarConsumable> listConsumable = carConsumableRepository.findByCar(car);

        List<CarConsumableDTO> collect = listConsumable.stream()
                .map(CarConsumableServiceImpl::entityToDTO).collect(Collectors.toList());


        return collect;
    }

    private static CarConsumableDTO entityToDTO(CarConsumable carConsumable) {
        CarConsumableDTO carConsumableDTO = CarConsumableDTO.builder()
                .consumableId(carConsumable.getConsumableId())
                .name(carConsumable.getName())
                .type(carConsumable.getType())
                .replaceCycle(carConsumable.getReplaceCycle())
                .replaceDate(carConsumable.getReplaceDate())
                .build();

        return carConsumableDTO;
    }


}
