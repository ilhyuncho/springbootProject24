package com.example.cih.service.carConsumable;

import com.example.cih.domain.car.*;
import com.example.cih.domain.carConsumable.CarConsumableRepository;
import com.example.cih.domain.reference.RefCarConsumableRepository;
import com.example.cih.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class CarStatisticsServiceImpl implements CarStatisticsService {

    private final UserService userService;
    private final CarRepository carRepository;
    private final CarConsumableRepository carConsumableRepository;
    private final RefCarConsumableRepository refCarConsumableRepository;


    @Override
    public void getStatisticsConsume(Long carid) {

        String[] types = {"m"};
        String keyword = "";

        carConsumableRepository.statisticsConsume(types, keyword);
    }

}
