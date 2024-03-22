package com.example.cih.service.car;

import com.example.cih.domain.car.Car;
import com.example.cih.dto.board.BoardResponseDTO;
import com.example.cih.dto.car.CarResponseDTO;

import java.util.List;

public interface CarService {
    List<CarResponseDTO> list();

}
