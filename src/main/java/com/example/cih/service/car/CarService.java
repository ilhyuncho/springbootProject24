package com.example.cih.service.car;

import com.example.cih.domain.car.Car;
import com.example.cih.dto.car.CarViewResDTO;

public interface CarService {
    CarViewResDTO readOne(Long carId);
    Car getCarInfo(Long carId);
}
