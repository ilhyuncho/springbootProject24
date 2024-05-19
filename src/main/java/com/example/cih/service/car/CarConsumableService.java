package com.example.cih.service.car;

import com.example.cih.dto.car.CarConsumableDTO;
import com.example.cih.dto.consumable.ConsumableRegDTO;

import java.util.List;

public interface CarConsumableService {
    List<CarConsumableDTO> readOne(Long carId);

    void registerConsumable(String userName, ConsumableRegDTO consumableRegDTO);
}
