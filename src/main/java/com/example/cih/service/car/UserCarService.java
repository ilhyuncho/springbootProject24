package com.example.cih.service.car;

import com.example.cih.controller.fileUpload.UploadFileDTO;
import com.example.cih.domain.car.Car;
import com.example.cih.domain.car.Projection;
import com.example.cih.dto.PageRequestDTO;
import com.example.cih.dto.car.CarInfoDTO;
import com.example.cih.dto.car.CarKmUpdateDTO;
import com.example.cih.dto.car.CarViewDTO;

import java.util.List;


public interface UserCarService {

    Long register(String userName, CarInfoDTO carInfoDTO, UploadFileDTO uploadFileDTO);

    CarViewDTO readMyCarDetailInfo(String userName, Long carId);

    List<CarViewDTO> readMyCarList(PageRequestDTO pageRequestDTO, String userName);
    List<Projection.CarSummary> readMyCarSummaryList(PageRequestDTO pageRequestDTO, String userName);

    void modifyMyCar(CarInfoDTO carInfoDTO);
    void modifyMyCarKm(CarKmUpdateDTO carKmUpdateDTO);
    void deleteMyCar(Long carId);
}
