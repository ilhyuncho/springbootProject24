package com.example.cih.service.car;

import com.example.cih.common.exception.OwnerCarNotFoundException;
import com.example.cih.domain.car.Car;
import com.example.cih.domain.car.CarConsumable;
import com.example.cih.domain.car.CarConsumableRepository;
import com.example.cih.domain.car.CarRepository;
import com.example.cih.domain.reference.RefCarConsumable;
import com.example.cih.domain.reference.RefCarConsumableRepository;
import com.example.cih.domain.user.User;
import com.example.cih.dto.car.CarConsumableDTO;
import com.example.cih.dto.consumable.ConsumableRegDTO;
import com.example.cih.service.user.UserService;
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

    private final UserService userService;
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
                    .consumableId(refCarConsumable.getRefConsumableId())
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

    @Override
    public void registerConsumable(String userName, ConsumableRegDTO consumableRegDTO){
        User user = userService.findUser(userName);

        RefCarConsumable refCarConsumable = refCarConsumableRepository.findById(consumableRegDTO.getConsumableId())
                .orElseThrow(() -> new NoSuchElementException("해당 소모품 정보가 존재하지않습니다"));

        Car car = carRepository.findById(consumableRegDTO.getCarId())
                .orElseThrow(() -> new OwnerCarNotFoundException("소유 차 정보가 존재하지않습니다"));

        // insert or update 처리를 동시에
        CarConsumable carConsumable1 = carConsumableRepository.
                findByCarAndRefConsumableId(car, consumableRegDTO.getConsumableId())
                .orElseGet(()-> createConsumable(consumableRegDTO, car));

        carConsumable1.changeReplaceDate(consumableRegDTO.getReplaceDate());
    }

    private CarConsumable createConsumable(ConsumableRegDTO consumableRegDTO, Car car){
        log.error("createConsumable~~~~~~~~~~~~~~~``");
        CarConsumable carConsumable = CarConsumable.builder()
                .refConsumableId(consumableRegDTO.getConsumableId())
                .replaceDate(consumableRegDTO.getReplaceDate())
                .car(car)
                .build();
        carConsumableRepository.save(carConsumable);

        return carConsumable;
    }
}
