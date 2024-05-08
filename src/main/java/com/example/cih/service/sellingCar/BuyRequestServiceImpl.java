package com.example.cih.service.sellingCar;

import com.example.cih.common.exception.OwnerCarNotFoundException;
import com.example.cih.domain.car.Car;
import com.example.cih.domain.car.CarRepository;
import com.example.cih.domain.sellingCar.BuyRequest;
import com.example.cih.domain.sellingCar.BuyRequestRepository;
import com.example.cih.domain.sellingCar.SellingCar;
import com.example.cih.domain.sellingCar.SellingCarRepository;
import com.example.cih.domain.user.User;
import com.example.cih.dto.sellingCar.BuyRequestRegDTO;
import com.example.cih.dto.sellingCar.BuyRequestViewDTO;
import com.example.cih.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class BuyRequestServiceImpl implements BuyRequestService {
    private final SellingCarRepository sellingCarRepository;
    private final BuyRequestRepository buyRequestRepository;

    private final UserService userService;
    private final CarRepository carRepository;

    @Override
    public List<BuyRequestViewDTO> getListBuyRequest(Long sellingCarId) {

        SellingCar sellingCar = sellingCarRepository.findById(sellingCarId)
                .orElseThrow(() -> new NoSuchElementException("해당 차 판매 정보가 존재하지않습니다"));

        List<BuyRequest> ListBuyRequest = buyRequestRepository.findBySellingCar(sellingCar);

        List<BuyRequestViewDTO> ListBuyRequestViewDTO = ListBuyRequest.stream()
                .map(BuyRequestServiceImpl::entityToDTO)
                .sorted(Comparator.comparing(BuyRequestViewDTO::getProposalPrice).reversed())   // 제안 가격 내림차순으로 정렬
                .collect(Collectors.toList());

        return ListBuyRequestViewDTO;
    }

    @Override
    public void registerBuyRequest(String userName, BuyRequestRegDTO buyRequestRegDTO) {
        User user = userService.findUser(userName);

        Car car = carRepository.findById(buyRequestRegDTO.getCarId())
                .orElseThrow(() -> new OwnerCarNotFoundException("소유 차 정보가 존재하지않습니다"));

        SellingCar sellingCar = sellingCarRepository.findById(car.getSellingCar().getSellingCarId())
                .orElseThrow(() -> new OwnerCarNotFoundException("소유 차 판매 정보가 존재하지않습니다"));

        BuyRequest buyRequest = BuyRequest.builder()
                .proposalPrice(buyRequestRegDTO.getRequestPrice())
                .user(user)
                .sellingCar(sellingCar)
                .build();

        buyRequestRepository.save(buyRequest);
    }

    private static BuyRequestViewDTO entityToDTO(BuyRequest buyRequest) {
        BuyRequestViewDTO buyRequestViewDTO = BuyRequestViewDTO.builder()
                .proposalPrice(buyRequest.getProposalPrice())
                .registerDate(buyRequest.getRegisterDate())
                .build();

        return buyRequestViewDTO;
    }

}
