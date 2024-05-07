package com.example.cih.service.auction;

import com.example.cih.domain.auction.Auction;
import com.example.cih.domain.auction.AuctionRepository;
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

    @Override
    public Long registerAuction(String userName, AuctionRegDTO auctionRegDTO) {

        User user = userService.findUser(userName);

        Auction auction = Auction.builder()
                        .carId(auctionRegDTO.getCarId())
                        .RequiredPrice(auctionRegDTO.getRequiredPrice())
                        .user(user)
                        .build();

        Auction save = auctionRepository.save(auction);

        return save.getAuctionid();
    }
}
