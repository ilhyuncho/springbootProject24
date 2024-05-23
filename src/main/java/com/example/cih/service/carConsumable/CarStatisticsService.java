package com.example.cih.service.carConsumable;

import com.example.cih.dto.car.CarConsumableDTO;
import com.example.cih.dto.car.CarConsumableInfoDTO;
import com.example.cih.dto.consumable.ConsumableRegDTO;
import com.example.cih.dto.history.HistoryCarDTO;

import java.util.List;

public interface CarStatisticsService {

    void getStatisticsConsume(Long carid);

}
