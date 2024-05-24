package com.example.cih.service.carConsumable;
import com.example.cih.dto.statistics.StatisticsResDTO;

import java.util.List;

public interface CarStatisticsService {

    List<StatisticsResDTO> getStatisticsConsume(Long carid);

}
