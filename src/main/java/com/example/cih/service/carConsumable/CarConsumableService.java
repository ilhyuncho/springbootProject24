package com.example.cih.service.carConsumable;

import com.example.cih.dto.car.CarConsumableDTO;
import com.example.cih.dto.car.CarConsumableInfoDTO;
import com.example.cih.dto.consumable.ConsumableRegDTO;
import com.example.cih.dto.history.HistoryCarDTO;

import java.util.List;

public interface CarConsumableService {
    List<CarConsumableDTO> getConsumableInfo(Long carId);
    List<CarConsumableInfoDTO> getConsumableDetail(Long carId, Long consumableId);
    List<HistoryCarDTO> getAllHistoryList(Long carId);
    List<HistoryCarDTO> getGasHistoryList(Long carId);
    List<HistoryCarDTO> getRepairHistoryList(Long carId);

    void registerConsumable(String userName, ConsumableRegDTO consumableRegDTO);

}
