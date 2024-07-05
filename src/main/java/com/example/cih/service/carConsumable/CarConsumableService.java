package com.example.cih.service.carConsumable;

import com.example.cih.dto.car.CarConsumableResDTO;
import com.example.cih.dto.car.CarConsumableDetailResDTO;
import com.example.cih.dto.car.CarConsumableRegDTO;
import com.example.cih.dto.history.HistoryCarResDTO;

import java.util.List;

public interface CarConsumableService {
    List<CarConsumableResDTO> getConsumableInfo(Long carId);
    List<CarConsumableDetailResDTO> getConsumableDetail(Long carId, Long consumableId);
    List<HistoryCarResDTO> getAllHistoryList(Long carId);
    List<HistoryCarResDTO> getGasHistoryList(Long carId);
    List<HistoryCarResDTO> getRepairHistoryList(Long carId);
    void registerConsumable(String userName, CarConsumableRegDTO carConsumableRegDTO);

}
