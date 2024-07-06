package com.example.cih.service.carConsumable;

import com.example.cih.domain.carConsumable.ConsumableType;
import com.example.cih.dto.car.CarConsumableResDTO;
import com.example.cih.dto.car.CarConsumableDetailResDTO;
import com.example.cih.dto.car.CarConsumableRegDTO;
import com.example.cih.dto.history.HistoryCarResDTO;

import java.util.List;

public interface CarConsumableService {
    CarConsumableDetailResDTO getConsumableInfo(Long consumableId);
    List<CarConsumableDetailResDTO> getConsumableDetail(Long carId, Long refConsumableId);
    void registerConsumable(CarConsumableRegDTO carConsumableRegDTO);
    void modifyConsumable(CarConsumableRegDTO carConsumableRegDTO);

    List<CarConsumableResDTO> getListConsumableInfo(Long carId);
    List<HistoryCarResDTO> getListHistory(Long carId, List<ConsumableType> listConsumableType);
//    List<HistoryCarResDTO> getListHistory(Long carId, ConsumableType consumableType);

}
