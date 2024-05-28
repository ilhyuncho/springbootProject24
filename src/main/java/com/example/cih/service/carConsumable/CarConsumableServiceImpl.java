package com.example.cih.service.carConsumable;

import com.example.cih.common.exception.OwnerCarNotFoundException;
import com.example.cih.domain.car.*;
import com.example.cih.domain.carConsumable.*;
import com.example.cih.domain.reference.RefCarConsumable;
import com.example.cih.domain.reference.RefCarConsumableRepository;
import com.example.cih.domain.user.User;
import com.example.cih.dto.car.CarConsumableDTO;
import com.example.cih.dto.car.CarConsumableInfoDTO;
import com.example.cih.dto.consumable.ConsumableRegDTO;
import com.example.cih.dto.history.HistoryCarDTO;
import com.example.cih.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Duration;
import java.time.LocalDate;
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

        // 1. RefConsumableId 별로 그룹핑
        // 2. 그룹별 ReplaceDate 가 가장 최신인 값 추출
        Map<RefCarConsumable, Optional<CarConsumable>> mapCarConsumable = carConsumableRepository.findByCar(car).stream()
                .collect(Collectors.groupingBy(CarConsumable::getRefConsumableId,
                        Collectors.maxBy(Comparator.comparing(CarConsumable::getReplaceDate))));

//        Map<Long, CarConsumable> mapCarConsumable = carConsumableRepository.findByCar(car).stream()
//                .collect(Collectors.toMap(CarConsumable::getRefConsumableId, a -> a));

        List<CarConsumableDTO> listCarConsumableDTO = new ArrayList<>();
        for (RefCarConsumable refCarConsumable : refListCarConsumable) {

            if(refCarConsumable.getRepairType().equals("주유")){
                continue;
            }
            CarConsumableDTO dto = CarConsumableDTO.builder()
                    .consumableId(refCarConsumable.getRefConsumableId())
                    .name(refCarConsumable.getName())
                    //.repairType(refCarConsumable.getRepairType())
                    .replaceCycleKm(refCarConsumable.getReplaceCycleKm())
                    .replaceCycleMonth(refCarConsumable.getReplaceCycleMonth())
                    .viewOrder(refCarConsumable.getViewOrder())
                    .build();

            // 유저가 이미 등록한 데이터가 있다면..
            if( mapCarConsumable.containsKey(refCarConsumable)){

                CarConsumable carConsumable = mapCarConsumable.get(refCarConsumable).get();

                // 다가오는 점검일 체크
                dto.setReplaceAlarm(checkNextReplaceDay(refCarConsumable, carConsumable));

                dto.setReplaceInfo(carConsumable.getReplacePrice(), carConsumable.getAccumKm(),
                        carConsumable.getReplaceShop(), carConsumable.getReplaceDate() );
            }

            listCarConsumableDTO.add(dto);
        }
        return listCarConsumableDTO;
    }

    @Override
    public List<HistoryCarDTO> getAllHistoryList(Long carId) {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new OwnerCarNotFoundException("차 정보가 존재하지않습니다"));

        List<CarConsumable> listCarConsumable = carConsumableRepository.findByCar(car);

        List<HistoryCarDTO> listHistoryCarDTO = listCarConsumable.stream()
                .map(CarConsumableServiceImpl::entityToDTO).collect(Collectors.toList());

        return listHistoryCarDTO;
    }
    @Override
    public List<HistoryCarDTO> getGasHistoryList(Long carId) {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new OwnerCarNotFoundException("차 정보가 존재하지않습니다"));

        List<CarConsumable> listCarConsumable = new ArrayList<>(carConsumableRepository
                .findByCarAndConsumableType(car, ConsumableType.GAS));

        List<HistoryCarDTO> listHistoryCarDTO = listCarConsumable.stream()
                .map(CarConsumableServiceImpl::entityToDTO).collect(Collectors.toList());

        return listHistoryCarDTO;
    }

    @Override
    public List<HistoryCarDTO> getRepairHistoryList(Long carId) {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new OwnerCarNotFoundException("차 정보가 존재하지않습니다"));

        List<CarConsumable> listCarConsumable = new ArrayList<>(carConsumableRepository
                .findByCarAndConsumableType(car, ConsumableType.REPAIR));

        List<HistoryCarDTO> listHistoryCarDTO = listCarConsumable.stream()
                .map(CarConsumableServiceImpl::entityToDTO).collect(Collectors.toList());

        return listHistoryCarDTO;
    }

    @Override
    public void registerConsumable(String userName, ConsumableRegDTO consumableRegDTO){
        User user = userService.findUser(userName);


        log.error("consumableRegDTO !!: " + consumableRegDTO);

        RefCarConsumable refCarConsumable = refCarConsumableRepository.findById(consumableRegDTO.getConsumableId())
                .orElseThrow(() -> new NoSuchElementException("해당 소모품 정보가 존재하지않습니다"));

        log.error("refCarConsumable : " + refCarConsumable.toString());

        Car car = carRepository.findById(consumableRegDTO.getCarId())
                .orElseThrow(() -> new OwnerCarNotFoundException("소유 차 정보가 존재하지않습니다"));

        // insert or update 처리를 동시에
//        CarConsumable carConsumable1 = carConsumableRepository.
//                findByCarAndRefConsumableId(car, consumableRegDTO.getConsumableId())
//                .orElseGet(()-> createConsumable(consumableRegDTO, car));
//
//        carConsumable1.changeReplaceInfo(consumableRegDTO.getReplacePrice(), consumableRegDTO.getAccumKm(),
//                consumableRegDTO.getReplaceShop(), consumableRegDTO.getReplaceDate());

        // 같은 날짜로 등록된 내역이 있는지 체크
        boolean present = carConsumableRepository.findByCarAndRefConsumableId(car, refCarConsumable)
                .stream().anyMatch(carConsumable -> carConsumable.getReplaceDate()
                        .equals(consumableRegDTO.getReplaceDate()));

        if(present){
            log.error("같은 날짜로 이미 등록 됨");
            throw new OwnerCarNotFoundException("같은 날짜로 이미 등록 됨.");
        }

        // 기존 내역 갱신이 아니라 새로 추가로 변경
        CarConsumable carConsumable = CarConsumable.builder()
                .refConsumableId(refCarConsumable)
                .replaceDate(consumableRegDTO.getReplaceDate())
                .replacePrice(consumableRegDTO.getReplacePrice())
                .accumKm(consumableRegDTO.getAccumKm())
                .gasLitter(consumableRegDTO.getGasLitter())
                .replaceShop(consumableRegDTO.getReplaceShop())
                .consumableType(consumableRegDTO.getConsumableType())
                //.repairType(RepairType.AIR_FILTER)

                .car(car)
                .build();
        carConsumableRepository.save(carConsumable);

    }

    @Override
    public List<CarConsumableInfoDTO> readDetailInfo(Long carId, Long consumableId){

        RefCarConsumable refCarConsumable = refCarConsumableRepository.findById(consumableId)
                .orElseThrow(() -> new OwnerCarNotFoundException("해당 소모품 정보가 존재하지않습니다"));

        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new OwnerCarNotFoundException("차 정보가 존재하지않습니다"));

        List<CarConsumable> listCarConsumable = carConsumableRepository.findByCarAndRefConsumableId(car, refCarConsumable);

        List<CarConsumableInfoDTO> listCarConsumableDTO = new ArrayList<>();
        for (CarConsumable carConsumable : listCarConsumable) {

            CarConsumableInfoDTO dto = CarConsumableInfoDTO.builder()
                    .repairType(refCarConsumable.getRepairType())
                    .replaceDate(carConsumable.getReplaceDate())
                    .accumKm(carConsumable.getAccumKm())
                    .replacePrice(carConsumable.getReplacePrice())
                    .replaceShop(carConsumable.getReplaceShop())
                    .build();

            listCarConsumableDTO.add(dto);
        }

        return listCarConsumableDTO;
    }

//    private CarConsumable createConsumable(ConsumableRegDTO consumableRegDTO, Car car){
//        log.error("createConsumable~~~~~~~~~~~~~~~``");
//        CarConsumable carConsumable = CarConsumable.builder()
//                .refConsumableId(consumableRegDTO.getConsumableId())
//                .replaceDate(consumableRegDTO.getReplaceDate())
//                .car(car)
//                .build();
//        carConsumableRepository.save(carConsumable);
//
//        return carConsumable;
//    }
    private static HistoryCarDTO entityToDTO(CarConsumable carConsumable) {
        HistoryCarDTO historyCarDTO = HistoryCarDTO.builder()
                .replacePrice(carConsumable.getReplacePrice())
                .gasLitter(carConsumable.getGasLitter())
                .accumKm(carConsumable.getAccumKm())
                .replaceShop(carConsumable.getReplaceShop())

                .repairType(carConsumable.getRefConsumableId().getName())
                .replaceDate(carConsumable.getReplaceDate())
                .build();

        return historyCarDTO;
    }
    private ReplaceAlarm checkNextReplaceDay(RefCarConsumable refCarConsumable, CarConsumable carConsumable){

        int cycleKm = refCarConsumable.getReplaceCycleKm();
        int cycleMonth = refCarConsumable.getReplaceCycleMonth();

        // 1. 개월 마다 체크
        if( cycleMonth > 0){
            LocalDate lastReplaceDate = carConsumable.getReplaceDate();
            LocalDate nextReplaceDay = lastReplaceDate.plusMonths(cycleMonth);  // 계산된 다음 점검 날짜

            log.error("lastReplaceDate : " + lastReplaceDate + ",  nextReplaceDay : " + nextReplaceDay);

            // 두 날짜 차이 계산
            long diffDays = Duration.between(LocalDate.now().atStartOfDay(), nextReplaceDay.atStartOfDay()).toDays();
            log.error(diffDays);

            if(diffDays <= 0 || (diffDays - 30) < 0){
                log.error("nextReplaceDay.READY_CYCLE(): " + nextReplaceDay + ", Diff: " + diffDays);
                return ReplaceAlarm.READY_CYCLE;
            }
            log.error("nextReplaceDay.NOT_CYCLE(): " + nextReplaceDay + ", Diff: " + diffDays);
        }
        else if( cycleKm > 0){   // 2. 주행 km 마다 체크
            int accumKm = carConsumable.getAccumKm();
            int nextReplaceKm = cycleKm + accumKm;

            // 주행 km를 비교하여 체크
        }

        return ReplaceAlarm.NOT_CYCLE;
    }

}
