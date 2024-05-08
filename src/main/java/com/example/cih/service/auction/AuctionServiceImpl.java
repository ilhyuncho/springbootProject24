package com.example.cih.service.auction;

import com.example.cih.common.exception.ItemNotFoundException;
import com.example.cih.common.exception.OwnerCarNotFoundException;
import com.example.cih.domain.auction.Auction;
import com.example.cih.domain.auction.AuctionRepository;
import com.example.cih.domain.car.Car;
import com.example.cih.domain.car.CarRepository;
import com.example.cih.domain.user.User;
import com.example.cih.dto.auction.AuctionRegDTO;
import com.example.cih.dto.auction.AuctionViewDTO;
import com.example.cih.dto.car.CarViewDTO;
import com.example.cih.service.car.UserCarServiceImpl;
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

    @Override
    public AuctionViewDTO getAuction(Long auctionId) {

        Auction auction = auctionRepository.findById(auctionId)
                .orElseThrow(() -> new NoSuchElementException("해당 경매 정보가 존재하지않습니다"));

        AuctionViewDTO auctionViewDTO = AuctionViewDTO.builder()
                .carId(auction.getCar().getCarId())
                .requiredPrice(auction.getRequiredPrice())
                .expiredDate(auction.getExpiredDate())
                .build();

        return auctionViewDTO;
    }

    @Override
    public List<AuctionViewDTO> getListAuction() {
        List<Auction> listAuction = auctionRepository.findAll();

        List<AuctionViewDTO> ListAuctionViewDTO = listAuction.stream().
                map(AuctionServiceImpl::entityToDTO).collect(Collectors.toList());

        return ListAuctionViewDTO;
    }

    private static AuctionViewDTO entityToDTO(Auction auction) {
        AuctionViewDTO auctionViewDTO = AuctionViewDTO.builder()
                .carId(auction.getCar().getCarId())
                .requiredPrice(auction.getRequiredPrice())
                .auctionStatus(auction.getAuctionStatus())
                .expiredDate(auction.getExpiredDate())
                .build();

        return auctionViewDTO;
    }



}
