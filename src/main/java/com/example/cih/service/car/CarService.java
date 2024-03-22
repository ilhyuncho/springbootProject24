package com.example.cih.service.car;

import com.example.cih.domain.car.Car;
import com.example.cih.dto.PageRequestDTO;
import com.example.cih.dto.PageResponseDTO;
import com.example.cih.dto.board.BoardResponseDTO;
import com.example.cih.dto.car.CarResponseDTO;

import java.util.List;

public interface CarService {
    PageResponseDTO<Car> list(PageRequestDTO pageRequestDTO);
    PageResponseDTO<Car> searchCarByKeyword(PageRequestDTO pageRequestDTO);

}
