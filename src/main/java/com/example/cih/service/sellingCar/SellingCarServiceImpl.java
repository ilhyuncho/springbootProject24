package com.example.cih.service.sellingCar;

import com.example.cih.common.exception.OwnerCarNotFoundException;
import com.example.cih.domain.buyingCar.BuyingCar;
import com.example.cih.domain.car.Car;
import com.example.cih.domain.car.CarImage;
import com.example.cih.domain.car.CarRepository;
import com.example.cih.domain.sellingCar.SellingCar;
import com.example.cih.domain.sellingCar.SellingCarRepository;
import com.example.cih.domain.sellingCar.SellingCarStatus;
import com.example.cih.domain.user.User;
import com.example.cih.domain.user.UserActionType;
import com.example.cih.domain.user.UserLike;
import com.example.cih.domain.user.UserLikeRepository;
import com.example.cih.dto.PageRequestDTO;
import com.example.cih.dto.PageResponseDTO;
import com.example.cih.dto.sellingCar.SellingCarRegDTO;
import com.example.cih.dto.sellingCar.SellingCarResDTO;
import com.example.cih.service.buyingCar.BuyingCarService;
import com.example.cih.service.car.CarService;
import com.example.cih.service.user.UserMissionService;
import com.example.cih.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class SellingCarServiceImpl implements SellingCarService {

    private final UserService userService;
    private final CarService carService;

    private final CarRepository carRepository;
    private final SellingCarRepository sellingCarRepository;
    private final UserLikeRepository userLikeRepository;
    private final UserMissionService userMissionService;
    private final BuyingCarService buyingCarService;


    @Override
    public void registerSellingCar(String userName, SellingCarRegDTO sellingCarRegDTO) {
        User user = userService.findUser(userName);

        Car car = carService.getCarInfo(sellingCarRegDTO.getCarId());

        if(car.getImageSet().size() == 0){
            throw new OwnerCarNotFoundException("차량 판매시 최소 한장의 대표 사진을 등록해야 합니다!!");
        }

        car.registerSellingCar(sellingCarRegDTO.getRequiredPrice());

        userMissionService.insertUserMission(userName, UserActionType.ACTION_REG_SELLING_CAR, car.getCarNumber());
    }

    @Override
    public SellingCarResDTO getSellingCarInfo(Long sellingCarId, User user) {

        // 판매 차량 정보 get
        SellingCar sellingCar = sellingCarRepository.findById(sellingCarId)
                .orElseThrow(() -> new NoSuchElementException("해당 차량 정보가 존재하지않습니다"));

        SellingCarResDTO sellingCarResDTO = entityToDTO(sellingCar);

       // 로그인 고객일 경우
        if( user != null ){
           // log.error(user.toString());

            // 해당 고객이 구매 요청을 했었는지 확인
            // 임시로
           // if(!Objects.equals(sellingCar.getUser().getUserId(), user.getUserId())){
                BuyingCar buyingCarInfo = buyingCarService.getBuyingCarInfo(user, sellingCar);
                if(buyingCarInfo != null){
                    sellingCarResDTO.setBuyCarStatus(buyingCarInfo.getBuyCarStatus());
                }

                log.error("buyingCarInfo: " + buyingCarInfo);
           // }

            // 좋아요 상태 전송
            userLikeRepository.findByUserAndSellingCar(user, sellingCar)
                    .ifPresent(userLike -> sellingCarResDTO.setIsLike(userLike.getIsLike()));

        }

        // 소유자 외의 고객이 검색 했을때
        if( user == null ||
                (user != null && !Objects.equals(user.getUserId(), sellingCar.getUser().getUserId())) ){
            sellingCar.changeViewCount();
        }

        return sellingCarResDTO;
    }

    @Override
    public PageResponseDTO<SellingCarResDTO> getListSellingCar(PageRequestDTO pageRequestDTO) {

        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable("regDate");

//        Page<SellingCar> sellingCars =
//                sellingCarRepository.findAllBySellingCarStatus(SellingCarStatus.PROCESSING, pageable);      // 진행 중인 것만 get

        // 검색 기능 추가 버전 ( querydsl
        Page<SellingCar> sellingCars = sellingCarRepository.searchAll(types, keyword, pageable);

        List<SellingCarResDTO> listSellingCarResDTO = sellingCars.getContent().stream()
                .map(SellingCarServiceImpl::entityToDTO)
//                .map(sellingCarViewDTO -> {  // 대표 이미지만 필터링 ( ImageOrder = 0 )
//                    sellingCarViewDTO.getFileNames().stream()
//                            .filter(carImage -> carImage.getImageOrder() != 0)
//                            .collect(Collectors.toList())
//                            .forEach(x -> sellingCarViewDTO.getFileNames().remove(x));
//                    return sellingCarViewDTO;
//                }
//                )
                .collect(Collectors.toList());

        return PageResponseDTO.<SellingCarResDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(listSellingCarResDTO)
                .total((int)sellingCars.getTotalElements())
                .build();
    }

    @Override
    public List<SellingCarResDTO> getRecommendList(){

        List<SellingCar> recommendSellingCar = sellingCarRepository.findRecommendSellingCar(4);
        for (SellingCar sellingCar : recommendSellingCar) {
            log.error(sellingCar);
        }

        List<SellingCarResDTO> listDTO = recommendSellingCar.stream()
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

        Car car = carService.getCarInfo(carId);

        SellingCar sellingCar = car.getSellingCar();
        if(SellingCarStatus.PROCESSING == sellingCar.getSellingCarStatus())
        {
            car.cancelCellingCar();
        }
        else{
             throw new OwnerCarNotFoundException("소유 차가 판매 중이 아닙니다");
        }
    }

    @Override
    public void likeSellingCar(User user, SellingCarRegDTO sellingCarRegDTO) {

        Car car = carService.getCarInfo(sellingCarRegDTO.getCarId());

        if(car.getSellingCar() != null){
            Optional<UserLike> userLike = userLikeRepository.findByUserAndSellingCar(user, car.getSellingCar());

            if(userLike.isPresent()){
                userLike.get().changeLike(sellingCarRegDTO.getIsLike());
            }
            else{
                userLikeRepository.save(UserLike.builder()
                        .user(user)
                        .sellingCar(car.getSellingCar())
                        .isLike(sellingCarRegDTO.getIsLike())
                        .build());
            }

            car.getSellingCar().changeLikeCount(sellingCarRegDTO.getIsLike());
        }
    }

    private static SellingCarResDTO entityToDTO(SellingCar sellingCar) {
        SellingCarResDTO sellingCarResDTO = SellingCarResDTO.builder()
                .carId(sellingCar.getCar().getCarId())
                .requiredPrice(sellingCar.getRequiredPrice())
                .sellingCarStatus(sellingCar.getSellingCarStatus())
                .expiredDate(sellingCar.getExpiredDate())
                .carNumber(sellingCar.getCar().getCarNumber())
                .carModel(sellingCar.getCar().getCarModel())
                .carYears(sellingCar.getCar().getCarYears())
                .sellingCarId(sellingCar.getSellingCarId())
                .viewCount(sellingCar.getViewCount())
                .isLike(false)
                .build();

        sellingCar.getCar().getImageSet()
                        .stream().sorted(Comparator.comparing(CarImage::getImageOrder))
                        .forEach(image -> {
            sellingCarResDTO.addImage(image.getCarImageId(), image.getUuid(), image.getFileName(),
                    image.getImageOrder(), image.getIsMainImage());
        });

        return sellingCarResDTO;
    }

}
