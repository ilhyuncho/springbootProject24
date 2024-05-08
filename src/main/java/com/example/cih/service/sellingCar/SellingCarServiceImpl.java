package com.example.cih.service.sellingCar;

import com.example.cih.common.exception.OwnerCarNotFoundException;
import com.example.cih.domain.car.Car;
import com.example.cih.domain.car.CarRepository;
import com.example.cih.domain.sellingCar.BuyRequestRepository;
import com.example.cih.domain.sellingCar.SellingCar;
import com.example.cih.domain.sellingCar.SellingCarRepository;
import com.example.cih.domain.user.User;
import com.example.cih.dto.sellingCar.SellingCarRegDTO;
import com.example.cih.dto.sellingCar.SellingCarViewDTO;
import com.example.cih.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class SellingCarServiceImpl implements SellingCarService {
    private final SellingCarRepository sellingCarRepository;

    private final UserService userService;
    private final CarRepository carRepository;

    @Override
    public void registerSellingCar(String userName, SellingCarRegDTO sellingCarRegDTO) {

        User user = userService.findUser(userName);

        Car car = carRepository.findById(sellingCarRegDTO.getCarId())
                .orElseThrow(() -> new OwnerCarNotFoundException("소유 차 정보가 존재하지않습니다"));

        car.registerSellingCar(sellingCarRegDTO.getRequiredPrice());
    }

    @Override
    public SellingCarViewDTO getSellingCar(Long sellingCarId) {

        SellingCar sellingCar = sellingCarRepository.findById(sellingCarId)
                .orElseThrow(() -> new NoSuchElementException("해당 경매 정보가 존재하지않습니다"));

        SellingCarViewDTO sellingCarViewDTO = SellingCarViewDTO.builder()
                .carId(sellingCar.getCar().getCarId())
                .requiredPrice(sellingCar.getRequiredPrice())
                .expiredDate(sellingCar.getExpiredDate())
                .build();

        return sellingCarViewDTO;
    }

    @Override
    public List<SellingCarViewDTO> getListSellingCar() {
        List<SellingCar> listSellingCar = sellingCarRepository.findAll();

        List<SellingCarViewDTO> sellingCarViewDTO = listSellingCar.stream().
                map(SellingCarServiceImpl::entityToDTO).collect(Collectors.toList());

        return sellingCarViewDTO;
    }

    private static SellingCarViewDTO entityToDTO(SellingCar sellingCar) {
        SellingCarViewDTO sellingCarViewDTO = SellingCarViewDTO.builder()
                .carId(sellingCar.getCar().getCarId())
                .requiredPrice(sellingCar.getRequiredPrice())
                .sellingCarStatus(sellingCar.getSellingCarStatus())
                .expiredDate(sellingCar.getExpiredDate())
                .carNumber(sellingCar.getCar().getCarNumber())
                .sellingCarId(sellingCar.getSellingCarId())
                .build();

        return sellingCarViewDTO;
    }

}
