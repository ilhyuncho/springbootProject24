package com.example.cih.service.car;

import com.example.cih.dto.PageRequestDTO;
import com.example.cih.dto.car.CarInfoDTO;
import com.example.cih.dto.car.CarKmUpdateDTO;
import com.example.cih.dto.car.CarViewDTO;
import com.example.cih.dto.car.CarViewNewDTO;

import java.util.List;


public interface UserCarService {

    Long register(String userName, String carNumber);

    CarViewNewDTO readMyCarDetailInfo(String userName, Long carId);

    List<CarViewNewDTO> readMyCarList(PageRequestDTO pageRequestDTO, String userName);

    void modifyMyCar(CarInfoDTO carInfoDTO);
    void modifyMyCarKm(CarKmUpdateDTO carKmUpdateDTO);
    void deleteMyCar(Long carId);
}
