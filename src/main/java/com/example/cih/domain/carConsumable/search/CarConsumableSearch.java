package com.example.cih.domain.carConsumable.search;
import com.example.cih.dto.statistics.StatisticsReqDTO;
import com.example.cih.dto.statistics.StatisticsResDTO;

import java.util.List;

public interface CarConsumableSearch {
    List<StatisticsResDTO> statisticsConsume(StatisticsReqDTO statisticsReqDTO);
    List<StatisticsResDTO> statisticsFuelEff(StatisticsReqDTO statisticsReqDTO);
}
