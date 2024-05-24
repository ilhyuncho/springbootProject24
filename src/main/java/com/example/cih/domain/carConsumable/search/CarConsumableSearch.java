package com.example.cih.domain.carConsumable.search;
import com.example.cih.dto.statistics.StatisticsResDTO;

import java.util.List;

public interface CarConsumableSearch {
    List<StatisticsResDTO> statisticsConsume(String[] types, String keyword);
}
