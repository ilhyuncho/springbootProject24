package com.example.cih.service.car;

import com.example.cih.domain.car.Car;
import com.example.cih.dto.car.CarViewResDTO;

public interface CarService {
    Car getCarInfo(Long carId);
    CarViewResDTO readOne(Long carId);
}
