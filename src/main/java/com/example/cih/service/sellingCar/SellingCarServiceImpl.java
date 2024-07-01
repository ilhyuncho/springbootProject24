package com.example.cih.service.sellingCar;

import com.example.cih.common.exception.OwnerCarNotFoundException;
import com.example.cih.domain.car.Car;
import com.example.cih.domain.car.CarImage;
import com.example.cih.domain.car.CarRepository;
import com.example.cih.domain.sellingCar.SellingCar;
import com.example.cih.domain.sellingCar.SellingCarRepository;
import com.example.cih.domain.sellingCar.SellingCarStatus;
import com.example.cih.domain.user.User;
import com.example.cih.domain.user.UserActionType;
import com.example.cih.dto.PageRequestDTO;
import com.example.cih.dto.PageResponseDTO;
import com.example.cih.dto.sellingCar.SellingCarRegDTO;
import com.example.cih.dto.sellingCar.SellingCarViewDTO;
import com.example.cih.service.user.UserMissionService;
import com.example.cih.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class SellingCarServiceImpl implements SellingCarService {
    private final SellingCarRepository sellingCarRepository;
    private final CarRepository carRepository;
    private final UserService userService;
    private final UserMissionService userMissionService;


    @Override
    public void registerSellingCar(String userName, SellingCarRegDTO sellingCarRegDTO) {
        User user = userService.findUser(userName);

        Car car = carRepository.findById(sellingCarRegDTO.getCarId())
                .orElseThrow(() -> new OwnerCarNotFoundException("소유 차 정보가 존재하지않습니다"));

        if(car.getImageSet().size() == 0){
            throw new OwnerCarNotFoundException("차량 판매시 최소 한장의 대표 사진을 등록해야 합니다!!");
        }

        car.registerSellingCar(sellingCarRegDTO.getRequiredPrice());

        userMissionService.insertUserMission(userName, UserActionType.ACTION_REG_SELLING_CAR, car.getCarNumber());
    }

    @Override
    public SellingCarViewDTO getSellingCar(Long sellingCarId) {

        SellingCar sellingCar = sellingCarRepository.findById(sellingCarId)
                .orElseThrow(() -> new NoSuchElementException("해당 차량 정보가 존재하지않습니다"));

        SellingCarViewDTO sellingCarViewDTO = entityToDTO(sellingCar);

        return sellingCarViewDTO;
    }

    @Override
    public PageResponseDTO<SellingCarViewDTO> getListSellingCar(PageRequestDTO pageRequestDTO) {

        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable("regDate");
        
        
//        Page<SellingCar> sellingCars =
//                sellingCarRepository.findAllBySellingCarStatus(SellingCarStatus.PROCESSING, pageable);      // 진행 중인 것만 get

        // 검색 기능 추가 버전 ( querydsl
        Page<SellingCar> sellingCars = sellingCarRepository.searchAll(types, keyword, pageable);

        List<SellingCarViewDTO> listSellingCarViewDTO = sellingCars.getContent().stream()
                .map(SellingCarServiceImpl::entityToDTO)
                .map(sellingCarViewDTO -> {  // 대표 이미지만 필터링 ( ImageOrder = 0 )
                    sellingCarViewDTO.getFileNames().stream()
                            .filter(carImage -> carImage.getImageOrder() != 0)
                            .collect(Collectors.toList())
                            .forEach(x -> sellingCarViewDTO.getFileNames().remove(x));
                    return sellingCarViewDTO;
                })
                .collect(Collectors.toList());

        return PageResponseDTO.<SellingCarViewDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(listSellingCarViewDTO)
                .total((int)sellingCars.getTotalElements())
                .build();
    }

    @Override
    public List<SellingCarViewDTO> getRecommendList(){

        List<SellingCar> recommendSellingCar = sellingCarRepository.findRecommendSellingCar(4);
        for (SellingCar sellingCar : recommendSellingCar) {
            log.error(sellingCar);
        }

        List<SellingCarViewDTO> listDTO = recommendSellingCar.stream()
                .map(SellingCarServiceImpl::entityToDTO)
                .map(sellingCarViewDTO -> {     // 대표 이미지만 필터링 ( ImageOrder = 0 )
                    sellingCarViewDTO.getFileNames().stream()
                            .filter(carImage -> carImage.getImageOrder() != 0)
                            .collect(Collectors.toList())
                            .forEach(x -> sellingCarViewDTO.getFileNames().remove(x));
                    return sellingCarViewDTO;
                })
                .collect(Collectors.toList());

        log.error(listDTO);

        return listDTO;
    }

    @Override
    public void cancelSellingCar(String userName, Long carId) {
        User user = userService.findUser(userName);

        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new OwnerCarNotFoundException("소유 차 정보가 존재하지않습니다"));

        SellingCar sellingCar = car.getSellingCar();
        if(SellingCarStatus.PROCESSING == sellingCar.getSellingCarStatus())
        {
            car.cancelCellingCar();
        }
        else{
             throw new OwnerCarNotFoundException("소유 차가 판매 중이 아닙니다");
        }
    }

    private static SellingCarViewDTO entityToDTO(SellingCar sellingCar) {
        SellingCarViewDTO sellingCarViewDTO = SellingCarViewDTO.builder()
                .carId(sellingCar.getCar().getCarId())
                .requiredPrice(sellingCar.getRequiredPrice())
                .sellingCarStatus(sellingCar.getSellingCarStatus())
                .expiredDate(sellingCar.getExpiredDate())
                .carNumber(sellingCar.getCar().getCarNumber())
                .carModel(sellingCar.getCar().getCarModel())
                .carYears(sellingCar.getCar().getCarYears())
                .sellingCarId(sellingCar.getSellingCarId())
                .build();

        sellingCar.getCar().getImageSet()
                        .stream().sorted(Comparator.comparing(CarImage::getImageOrder))
                        .forEach(image -> {
            sellingCarViewDTO.addImage(image.getUuid(), image.getFileName(), image.getImageOrder());
        });

        return sellingCarViewDTO;
    }

}
