package com.example.cih.service.car;

import com.example.cih.domain.board.Board;
import com.example.cih.domain.board.BoardRepository;
import com.example.cih.domain.car.Car;
import com.example.cih.domain.car.CarRepository;
import com.example.cih.dto.board.BoardResponseDTO;
import com.example.cih.dto.car.CarResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<CarResponseDTO> list() {

        List<Car> findResult = carRepository.findAll();

        List<CarResponseDTO> result = findResult.stream()
                .map(car -> modelMapper.map(car, CarResponseDTO.class))
                .collect(Collectors.toList());

        result.forEach(log::error);

        return result;
    }
}
