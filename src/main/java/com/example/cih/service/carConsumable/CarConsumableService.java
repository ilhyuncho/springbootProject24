package com.example.cih.service.carConsumable;

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
    List<HistoryCarResDTO> getListAllHistory(Long carId);
    List<HistoryCarResDTO> getListGasHistory(Long carId);
    List<HistoryCarResDTO> getListRepairHistory(Long carId);



}
