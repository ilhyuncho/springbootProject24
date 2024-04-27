package com.example.cih.service.car;

import com.example.cih.controller.fileUpload.UploadFileDTO;
import com.example.cih.domain.car.Car;
import com.example.cih.domain.car.Projection;
import com.example.cih.dto.PageRequestDTO;
import com.example.cih.dto.car.CarInfoDTO;
import com.example.cih.dto.car.CarSpecDTO;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.stream.Collectors;


public interface UserCarService {

    Long register(String userName, CarInfoDTO carInfoDTO, UploadFileDTO uploadFileDTO);

    List<CarInfoDTO> readMyCarList(PageRequestDTO pageRequestDTO, String userName);
    List<Projection.CarSummary> readMyCarSummaryList(PageRequestDTO pageRequestDTO, String userName);
}
