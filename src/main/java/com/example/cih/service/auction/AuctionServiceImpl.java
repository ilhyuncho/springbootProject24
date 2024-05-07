package com.example.cih.service.auction;

import com.example.cih.common.exception.ItemNotFoundException;
import com.example.cih.common.exception.OwnerCarNotFoundException;
import com.example.cih.domain.auction.Auction;
import com.example.cih.domain.auction.AuctionRepository;
import com.example.cih.domain.car.Car;
import com.example.cih.domain.car.CarRepository;
import com.example.cih.domain.user.User;
import com.example.cih.dto.auction.AuctionRegDTO;
import com.example.cih.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class AuctionServiceImpl implements AuctionService {
    private final AuctionRepository auctionRepository;
    private final UserService userService;
    private final CarRepository carRepository;

    @Override
    public void registerAuction(String userName, AuctionRegDTO auctionRegDTO) {

        User user = userService.findUser(userName);

        Car car = carRepository.findById(auctionRegDTO.getCarId())
                .orElseThrow(() -> new OwnerCarNotFoundException("소유 차 정보가 존재하지않습니다"));

        car.registerAuction(auctionRegDTO.getRequiredPrice());
    }
}
