package com.example.cih.service.carConsumable;

import com.example.cih.common.exception.OwnerCarNotFoundException;
import com.example.cih.domain.car.*;
import com.example.cih.domain.carConsumable.*;
import com.example.cih.domain.reference.RefCarConsumable;
import com.example.cih.domain.reference.RefCarConsumableRepository;
import com.example.cih.dto.car.CarConsumableResDTO;
import com.example.cih.dto.car.CarConsumableDetailResDTO;
import com.example.cih.dto.car.CarConsumableRegDTO;
import com.example.cih.dto.history.HistoryCarResDTO;
import com.example.cih.service.car.CarService;
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

    private final CarService carService;
    private final CarConsumableRepository carConsumableRepository;
    private final RefCarConsumableRepository refCarConsumableRepository;

    // 해당 consumableId 정보
    public CarConsumable getCarConsumableInfo(Long consumableId){
        return carConsumableRepository.findById(consumableId)
                .orElseThrow(() -> new OwnerCarNotFoundException("해당 소모품 정보가 존재하지않습니다"));
    }

    public RefCarConsumable getRefCarConsumableInfo(Long refConsumableId){
        return refCarConsumableRepository.findById(refConsumableId)
                .orElseThrow(() -> new NoSuchElementException("해당 소모품 정보가 존재하지않습니다"));
    }

    // 해당 차의 소모품 교환 내역별 최신 값 get
    public Map<RefCarConsumable, Optional<CarConsumable>> getMapRecentConsumableInfo(Car car){
        // 1. RefConsumableId 별로 그룹핑
        // 2. 그룹별 ReplaceDate 가 가장 최신인 값 추출
        return carConsumableRepository.findByCar(car).stream()
                        .collect(Collectors.groupingBy(CarConsumable::getRefCarConsumable,
                                Collectors.maxBy(Comparator.comparing(CarConsumable::getReplaceDate))));
    }

    public List<RefCarConsumable> getLitRefCarConsumableInfo(){
        return refCarConsumableRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(RefCarConsumable::getViewOrder))
                .collect(Collectors.toList());
    }

    @Override
    public List<CarConsumableResDTO> getListConsumableInfo(Long carId){ // 소모품 관리 전체 내역 및 해당 차의 소모품 별 최신 관리 내역 return

        Car car = carService.getCarInfo(carId);

        // 해당 차의 소모품 교환 내역별 최신 값 get
        Map<RefCarConsumable, Optional<CarConsumable>> mapCarConsumable = getMapRecentConsumableInfo(car);

        // 전체 ref 소모품 종류 get
        List<RefCarConsumable> listRefCarConsumable = getLitRefCarConsumableInfo();

        return listRefCarConsumable.stream()
                .filter(refCarConsumable -> !refCarConsumable.getName().equals("주유"))
                .map(refCarConsumable -> {
                    CarConsumableResDTO dto = entityToDTO(refCarConsumable);

                    // 고객이 이미 등록한 데이터가 있다면..
                    if(mapCarConsumable.containsKey(refCarConsumable)){

                        CarConsumable carConsumable = mapCarConsumable.get(refCarConsumable).get();
                        // 해당 소모품 최신 정비 내역 set
                        dto.setRecentReplaceInfo(refCarConsumable, carConsumable);
                    }
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<CarConsumableDetailResDTO> getConsumableDetail(Long carId, Long refConsumableId){   // 소모품 히스토리 리스트

        Car car = carService.getCarInfo(carId);

        RefCarConsumable refCarConsumable = getRefCarConsumableInfo(refConsumableId);

        return carConsumableRepository.findByCarAndRefCarConsumable(car, refCarConsumable)
                .stream()
                .map(CarConsumableServiceImpl::entityToDetailDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CarConsumableDetailResDTO getConsumableInfo(Long consumableId) {

        CarConsumable carConsumable = getCarConsumableInfo(consumableId);

        return entityToDetailDTO(carConsumable);
    }

    // 같은 날짜로 등록된 내역이 있는지 체크
    public Boolean isExistCarConsumableHistory(Car car, RefCarConsumable refCarConsumable, LocalDate date){
        return carConsumableRepository.findByCarAndRefCarConsumable(car, refCarConsumable)
                .stream()
                .anyMatch(carConsumable -> carConsumable.getReplaceDate()
                .equals(date));
    }

    @Override
    public void registerConsumable(CarConsumableRegDTO carConsumableRegDTO){

        Car car = carService.getCarInfo(carConsumableRegDTO.getCarId());

        RefCarConsumable refCarConsumable = getRefCarConsumableInfo(carConsumableRegDTO.getRefConsumableId());

        log.error("refCarConsumable : " + refCarConsumable.toString());

        CarConsumable carConsumable = CarConsumable.builder()
                .refCarConsumable(refCarConsumable)
                .car(car)
                .replaceDate(carConsumableRegDTO.getReplaceDate())
                .replacePrice(carConsumableRegDTO.getReplacePrice())
                .accumKm(carConsumableRegDTO.getAccumKm())
                .gasLitter(carConsumableRegDTO.getGasLitter())
                .replaceShop(carConsumableRegDTO.getReplaceShop())
                .consumableType(carConsumableRegDTO.getConsumableType())
                .build();

        carConsumableRepository.save(carConsumable);
    }

    @Override
    public void modifyConsumable(CarConsumableRegDTO carConsumableRegDTO) {

        CarConsumable carConsumable = getCarConsumableInfo(carConsumableRegDTO.getConsumableId());

        carConsumable.setConsumableInfo(carConsumableRegDTO);
    }

    @Override
    public void removeConsumable(Long consumableId) {
        CarConsumable carConsumable = getCarConsumableInfo(consumableId);

        carConsumableRepository.delete(carConsumable);
    }

    @Override
    public List<HistoryCarResDTO> getListHistory(Long carId, List<ConsumableType> listConsumableType) {

       Car car = carService.getCarInfo(carId);

       return carConsumableRepository.findByConsumableTypes(car, listConsumableType)
               .stream()
               .map(CarConsumableServiceImpl::entityToHistoryDTO)
               .sorted(Comparator.comparing(HistoryCarResDTO::getReplaceDate))
               .collect(Collectors.toList());
    }

    private static CarConsumableResDTO entityToDTO(RefCarConsumable refCarConsumable) {
        return CarConsumableResDTO.builder()
                .refConsumableId(refCarConsumable.getRefConsumableId())
                .name(refCarConsumable.getName())
                .repairType(refCarConsumable.getRepairType())
                .replaceCycleKm(refCarConsumable.getReplaceCycleKm())
                .replaceCycleMonth(refCarConsumable.getReplaceCycleMonth())
                .viewOrder(refCarConsumable.getViewOrder())
                .build();
    }

    private static CarConsumableDetailResDTO entityToDetailDTO(CarConsumable carConsumable) {
        return CarConsumableDetailResDTO.builder()
                .consumableId(carConsumable.getConsumableId())
                .refConsumableId(carConsumable.getRefCarConsumable().getRefConsumableId())
                .carId(carConsumable.getCar().getCarId())
                .replaceDate(carConsumable.getReplaceDate())
                .replacePrice(carConsumable.getReplacePrice())
                .replaceShop(carConsumable.getReplaceShop())
                .accumKm(carConsumable.getAccumKm())
                .repairName(carConsumable.getRefCarConsumable().getName())
                .build();
    }

    private static HistoryCarResDTO entityToHistoryDTO(CarConsumable carConsumable) {
        return HistoryCarResDTO.builder()
                .replacePrice(carConsumable.getReplacePrice())
                .gasLitter(carConsumable.getGasLitter())
                .accumKm(carConsumable.getAccumKm())
                .replaceShop(carConsumable.getReplaceShop())
                .repairType(carConsumable.getRefCarConsumable().getName())
                .replaceDate(carConsumable.getReplaceDate())
                .build();
    }

    //    @Override
//    public List<HistoryCarResDTO> getListHistory(Long carId, ConsumableType consumableType) {
//
//        Car car = carService.getCarInfo(carId);
//
//        List<CarConsumable> listCarConsumable = new ArrayList<>(carConsumableRepository
//                .findByCarAndConsumableType(car, consumableType));
//
//        return listCarConsumable.stream()
//                .map(CarConsumableServiceImpl::entityToHistoryDTO)
//                .sorted(Comparator.comparing(HistoryCarResDTO::getReplaceDate))
//                .collect(Collectors.toList());
//    }

}
