package com.example.cih.service.car;

import com.example.cih.domain.car.Car;
import com.example.cih.domain.car.CarRepository;
import com.example.cih.domain.car.CarSize;
import com.example.cih.dto.PageRequestDTO;
import com.example.cih.dto.PageResponseDTO;

import com.example.cih.dto.car.CarDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final ModelMapper modelMapper;

    @Override
    public PageResponseDTO<Car> list(PageRequestDTO pageRequestDTO) {

        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable("carGrade");

        //List<Car> findResult = carRepository.findAll();

//        List<CarResponseDTO> result = findResult.stream()
//                .map(car -> modelMapper.map(car, CarResponseDTO.class))
//                .collect(Collectors.toList());
//
//        result.forEach(log::error);

        Page<Car> result = carRepository.findAll(pageable);

        log.error("=================list=====================");
        //content.forEach(log::error);

        return PageResponseDTO.<Car>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(result.getContent())
                .total((int)result.getTotalElements())
                .build();
    }

    @Override
    public PageResponseDTO<Car> searchCarByKeyword(PageRequestDTO pageRequestDTO) {
        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable("carGrade");

        keyword ="model4";
        final Page<Car> result = carRepository.findByCarModelContaining(keyword, pageable);

        log.error("===============searchCarByKeyword=======================");
        //content.forEach(log::error);

        return PageResponseDTO.<Car>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(result.getContent())
                .total((int)result.getTotalElements())
                .build();
    }

    @Override
    public CarDTO readOne(Long carId) {
        Optional<Car> result = carRepository.findById(carId);

        Car car = result.orElseThrow();

        return modelMapper.map(car, CarDTO.class);
    }
}
