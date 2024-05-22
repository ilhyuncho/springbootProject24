package com.example.cih.service.car;

import com.example.cih.dto.car.CarConsumableDTO;
import com.example.cih.dto.car.CarConsumableInfoDTO;
import com.example.cih.dto.consumable.ConsumableRegDTO;
import com.example.cih.dto.history.HistoryCarDTO;

import java.util.List;

public interface CarConsumableService {
    List<CarConsumableDTO> readOne(Long carId);
    List<HistoryCarDTO> getAllHistoryList(Long carId);
    List<HistoryCarDTO> getGasHistoryList(Long carId);
    List<HistoryCarDTO> getRepairHistoryList(Long carId);
    List<CarConsumableInfoDTO> readDetailInfo(Long carId, Long consumableId);
    void registerConsumable(String userName, ConsumableRegDTO consumableRegDTO);

}
