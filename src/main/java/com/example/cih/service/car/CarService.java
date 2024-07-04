package com.example.cih.service.car;

import com.example.cih.dto.car.CarViewDTO;

public interface CarService {
    CarViewDTO readOne(Long carId);
}
