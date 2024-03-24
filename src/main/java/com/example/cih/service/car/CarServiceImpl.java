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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final ModelMapper modelMapper;

    @Override
    public PageResponseDTO<CarDTO> list(PageRequestDTO pageRequestDTO) {

        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable("carGrade");

        Page<Car> result = carRepository.findAll(pageable);

        List<CarDTO> dtoList = result.getContent().stream()
                .map(car -> modelMapper.map(car, CarDTO.class)).collect(Collectors.toList());

//        dtoList.forEach(log::error);
//        log.error(result.getTotalPages());
//        log.error(result.getTotalElements());
//        log.error(result.getTotalPages());

        return PageResponseDTO.<CarDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int)result.getTotalElements())
                .build();
    }

    @Override
    public PageResponseDTO<CarDTO> searchCarByKeyword(PageRequestDTO pageRequestDTO) {
        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable("carGrade");

        final Page<Car> result = carRepository.findByCarModelContaining(keyword, pageable);

        List<CarDTO> dtoList = result.getContent().stream()
                .map(car -> modelMapper.map(car, CarDTO.class)).collect(Collectors.toList());

        //content.forEach(log::error);

        return PageResponseDTO.<CarDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
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
