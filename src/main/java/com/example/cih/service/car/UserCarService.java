package com.example.cih.service.car;

import com.example.cih.dto.PageRequestDTO;
import com.example.cih.dto.car.*;

import java.util.List;


public interface UserCarService {

    List<CarViewResDTO> readMyCarList(PageRequestDTO pageRequestDTO, String userName);
    CarViewResDTO readMyCarDetailInfo(String userName, Long carId);

    Long registerMyCar(String userName, String carNumber);

    void modifyMyCar(CarInfoReqDTO carInfoReqDTO);
    void modifyMyCarKm(CarKmUpdateReqDTO carKmUpdateReqDTO);
    void deleteMyCar(Long carId);
}
