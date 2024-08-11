package com.example.cih.service.car;

import com.example.cih.dto.PageRequestDTO;
import com.example.cih.dto.car.*;

import java.util.List;


public interface UserCarService {

    List<CarViewResDTO> readMyCarList(PageRequestDTO pageRequestDTO, String memberId);
    CarViewResDTO readMyCarDetailInfo(String memberId, Long carId);

    Long registerMyCar(String memberId, String carNumber);

    void modifyMyCar(CarInfoReqDTO carInfoReqDTO);
    void modifyMyCarKm(CarKmUpdateReqDTO carKmUpdateReqDTO);
    void deleteMyCar(Long carId);
}
