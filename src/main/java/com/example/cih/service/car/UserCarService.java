package com.example.cih.service.car;

import com.example.cih.domain.user.User;
import com.example.cih.dto.PageRequestDTO;
import com.example.cih.dto.car.*;

import java.util.List;


public interface UserCarService {

    List<CarViewResDTO> readMyCarList(User user);
    CarViewResDTO readMyCarDetailInfo(User user, Long carId);

    Long registerMyCar(User user, String carNumber);
    void modifyMyCar(CarInfoReqDTO carInfoReqDTO);
    void modifyMyCarKm(CarKmUpdateReqDTO carKmUpdateReqDTO);
    void deleteMyCar(Long carId);
}
