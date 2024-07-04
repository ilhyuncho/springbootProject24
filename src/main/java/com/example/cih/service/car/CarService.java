package com.example.cih.service.car;

import com.example.cih.dto.car.CarViewResDTO;

public interface CarService {
    CarViewResDTO readOne(Long carId);
}
