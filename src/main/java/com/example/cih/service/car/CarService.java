package com.example.cih.service.car;

import com.example.cih.dto.car.CarViewNewDTO;

public interface CarService {
    CarViewNewDTO readOne(Long carId);
}
