package com.example.cih.service.car;

import com.example.cih.dto.car.CarConsumableDTO;
import com.example.cih.dto.car.CarConsumableInfoDTO;
import com.example.cih.dto.consumable.ConsumableRegDTO;

import java.util.List;

public interface CarConsumableService {
    List<CarConsumableDTO> readOne(Long carId);
    List<CarConsumableDTO> getGasHistoryList(Long carId);
    List<CarConsumableInfoDTO> readDetailInfo(Long carId, Long consumableId);
    void registerConsumable(String userName, ConsumableRegDTO consumableRegDTO);

}
