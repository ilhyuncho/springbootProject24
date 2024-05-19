package com.example.cih.service.car;

import com.example.cih.common.exception.OwnerCarNotFoundException;
import com.example.cih.domain.car.Car;
import com.example.cih.domain.car.CarConsumable;
import com.example.cih.domain.car.CarConsumableRepository;
import com.example.cih.domain.car.CarRepository;
import com.example.cih.domain.reference.RefCarConsumable;
import com.example.cih.domain.reference.RefCarConsumableRepository;
import com.example.cih.dto.car.CarConsumableDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class CarConsumableServiceImpl implements CarConsumableService {

    private final CarRepository carRepository;
    private final CarConsumableRepository carConsumableRepository;
    private final RefCarConsumableRepository refCarConsumableRepository;

    @Override
    public List<CarConsumableDTO> readOne(Long carId){

        List<RefCarConsumable> refListCarConsumable = refCarConsumableRepository.findAll()
                .stream().sorted(Comparator.comparing(RefCarConsumable::getViewOrder)).collect(Collectors.toList());

        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new OwnerCarNotFoundException("차 정보가 존재하지않습니다"));

        Map<Long, LocalDateTime> mapUserReplaceDate = carConsumableRepository.findByCar(car).stream()
                .collect(Collectors.toMap(CarConsumable::getRefConsumableId, CarConsumable::getReplaceDate));

        List<CarConsumableDTO> listCarConsumableDTO = new ArrayList<>();
        for (RefCarConsumable refCarConsumable : refListCarConsumable) {

            CarConsumableDTO dto = CarConsumableDTO.builder()
                    .name(refCarConsumable.getName())
                    .repairType(refCarConsumable.getRepairType())
                    .replaceCycleKm(refCarConsumable.getReplaceCycleKm())
                    .replaceCycleMonth(refCarConsumable.getReplaceCycleMonth())
                    .viewOrder(refCarConsumable.getViewOrder())
                   // .replaceDate(mapUserReplaceDate.getOrDefault(refCarConsumable.getRefConsumableId(), null ))
                    .replaceDate(mapUserReplaceDate.computeIfAbsent(refCarConsumable.getRefConsumableId(), k -> null ))
                    .build();
            listCarConsumableDTO.add(dto);
        }

//        for (CarConsumableDTO carConsumableDTO : listCarConsumableDTO) {
//            log.error(carConsumableDTO.toString());
//        }

        return listCarConsumableDTO;
    }

}
