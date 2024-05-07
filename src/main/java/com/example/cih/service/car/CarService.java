package com.example.cih.service.car;

import com.example.cih.domain.car.Car;
import com.example.cih.dto.PageRequestDTO;
import com.example.cih.dto.PageResponseDTO;
import com.example.cih.dto.car.CarInfoDTO;


import java.util.List;

public interface CarService {
    PageResponseDTO<CarInfoDTO> list(PageRequestDTO pageRequestDTO);
    PageResponseDTO<CarInfoDTO> searchCarByKeyword(PageRequestDTO pageRequestDTO);
    CarInfoDTO readOne(Long carId);
}
