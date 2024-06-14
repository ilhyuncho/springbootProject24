package com.example.cih.service.car;

import com.example.cih.dto.PageRequestDTO;
import com.example.cih.dto.car.*;

import java.util.List;


public interface UserCarService {

    List<CarViewNewDTO> readMyCarList(PageRequestDTO pageRequestDTO, String userName);
    CarViewNewDTO readMyCarDetailInfo(String userName, Long carId);

    Long register(String userName, String carNumber);

    void modifyMyCar(CarInfoNewDTO carInfoDTO);
    void modifyMyCarKm(CarKmUpdateDTO carKmUpdateDTO);
    void deleteMyCar(Long carId);
}
