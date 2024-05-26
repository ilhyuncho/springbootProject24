package com.example.cih.service.carConsumable;

import com.example.cih.common.exception.OwnerCarNotFoundException;
import com.example.cih.domain.car.*;
import com.example.cih.domain.carConsumable.CarConsumableRepository;
import com.example.cih.dto.statistics.StatisticsReqDTO;
import com.example.cih.dto.statistics.StatisticsResDTO;
import com.example.cih.dto.statistics.StatisticsTotalResDTO;
import com.example.cih.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class CarStatisticsServiceImpl implements CarStatisticsService {

    private final UserService userService;
    private final CarRepository carRepository;
    private final CarConsumableRepository carConsumableRepository;


    @Override
    public List<StatisticsResDTO> getStatisticsConsume(StatisticsReqDTO statisticsReqDTO) {

        Car car = carRepository.findById(statisticsReqDTO.getCarId())
                .orElseThrow(() -> new OwnerCarNotFoundException("차 정보가 존재하지않습니다"));

        List<StatisticsResDTO> listDto = carConsumableRepository.statisticsConsume(statisticsReqDTO);

        return listDto;
    }

    @Override
    public List<StatisticsResDTO> getStatisticsFuelAmount(StatisticsReqDTO statisticsReqDTO) {

        Car car = carRepository.findById(statisticsReqDTO.getCarId())
                .orElseThrow(() -> new OwnerCarNotFoundException("차 정보가 존재하지않습니다"));

        List<StatisticsResDTO> listDto = carConsumableRepository.statisticsFuelAmount(statisticsReqDTO);

        return listDto;
    }

    @Override
    public List<StatisticsResDTO> getStatisticsDistance(StatisticsReqDTO statisticsReqDTO) {
        Car car = carRepository.findById(statisticsReqDTO.getCarId())
                .orElseThrow(() -> new OwnerCarNotFoundException("차 정보가 존재하지않습니다"));

        List<StatisticsResDTO> listDto = carConsumableRepository.statisticsDistance(statisticsReqDTO);

        return listDto;
    }

    @Override
    public StatisticsTotalResDTO getStatisticsTotal(StatisticsReqDTO statisticsReqDTO) {
        Car car = carRepository.findById(statisticsReqDTO.getCarId())
                .orElseThrow(() -> new OwnerCarNotFoundException("차 정보가 존재하지않습니다"));

        StatisticsTotalResDTO totalDTO = carConsumableRepository.statisticsTotal(statisticsReqDTO);

        return totalDTO;
    }

}
