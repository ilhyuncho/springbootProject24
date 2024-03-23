package com.example.cih.service.car;

import com.example.cih.domain.car.Car;
import com.example.cih.dto.PageRequestDTO;
import com.example.cih.dto.PageResponseDTO;
import com.example.cih.dto.car.CarDTO;


import java.util.List;

public interface CarService {
    PageResponseDTO<Car> list(PageRequestDTO pageRequestDTO);
    PageResponseDTO<Car> searchCarByKeyword(PageRequestDTO pageRequestDTO);
    CarDTO readOne(Long carId);

}
