package com.example.cih.service.buyingCar;

import com.example.cih.common.exception.OwnerCarNotFoundException;
import com.example.cih.domain.buyingCar.BuyingCar;
import com.example.cih.domain.buyingCar.BuyingCarRepository;
import com.example.cih.domain.car.Car;
import com.example.cih.domain.car.CarRepository;
import com.example.cih.domain.sellingCar.SellingCar;
import com.example.cih.domain.sellingCar.SellingCarRepository;
import com.example.cih.domain.user.User;
import com.example.cih.dto.buyingCar.BuyingCarRegDTO;
import com.example.cih.dto.buyingCar.BuyingCarViewDTO;
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
public class BuyingCarServiceImpl implements BuyingCarService {
    private final SellingCarRepository sellingCarRepository;
    private final BuyingCarRepository buyingCarRepository;

    private final UserService userService;
    private final CarRepository carRepository;

    @Override
    public List<BuyingCarViewDTO> getListBuyingCar(Long sellingCarId) {

        SellingCar sellingCar = sellingCarRepository.findById(sellingCarId)
                .orElseThrow(() -> new NoSuchElementException("해당 차 판매 정보가 존재하지않습니다"));

        List<BuyingCar> ListBuyingCar = buyingCarRepository.findBySellingCar(sellingCar);

        List<BuyingCarViewDTO> ListBuyingCarViewDTO = ListBuyingCar.stream()
                .map(BuyingCarServiceImpl::entityToDTO)
                .sorted(Comparator.comparing(BuyingCarViewDTO::getProposalPrice).reversed())   // 제안 가격 내림차순으로 정렬
                .collect(Collectors.toList());

        return ListBuyingCarViewDTO;
    }
    @Override
    public void registerBuyingCar(User user, BuyingCarRegDTO buyingCarRegDTO) {

        SellingCar sellingCar = getSellingCarInfo(buyingCarRegDTO);

        BuyingCar buyingCar = BuyingCar.builder()
                .proposalPrice(buyingCarRegDTO.getRequestPrice())
                .user(user)
                .sellingCar(sellingCar)
                .build();

        buyingCarRepository.save(buyingCar);
    }
    @Override
    public void modifyBuyingCar(User user, BuyingCarRegDTO buyingCarRegDTO) {

        SellingCar sellingCar = getSellingCarInfo(buyingCarRegDTO);

        BuyingCar buyingCar = getBuyingCarInfo(sellingCar, user);

        buyingCar.changePrice(buyingCarRegDTO.getRequestPrice());
    }

    @Override
    public void deleteBuyingCar(User user, BuyingCarRegDTO buyingCarRegDTO) {

        SellingCar sellingCar = getSellingCarInfo(buyingCarRegDTO);

        BuyingCar buyingCar = getBuyingCarInfo(sellingCar, user);

        buyingCarRepository.delete(buyingCar);
    }

    public SellingCar getSellingCarInfo(BuyingCarRegDTO buyingCarRegDTO){

        Car car = carRepository.findById(buyingCarRegDTO.getCarId())
                .orElseThrow(() -> new OwnerCarNotFoundException("차 정보가 존재하지않습니다"));

        return sellingCarRepository.findById(car.getSellingCar().getSellingCarId())
                .orElseThrow(() -> new OwnerCarNotFoundException("차 판매 정보가 존재하지않습니다"));
    }

    public BuyingCar getBuyingCarInfo(SellingCar sellingCar, User user){

        return buyingCarRepository.findBySellingCarAndUser(sellingCar, user)
                .orElseThrow(() -> new OwnerCarNotFoundException("가격 제안 정보가 존재하지않습니다"));
    }

    private static BuyingCarViewDTO entityToDTO(BuyingCar buyingCar) {
        BuyingCarViewDTO buyingCarViewDTO = BuyingCarViewDTO.builder()
                .proposalPrice(buyingCar.getProposalPrice())
                .registerDate(buyingCar.getRegisterDate())
                .userName(buyingCar.getUser().getUserName())
                .build();

        return buyingCarViewDTO;
    }

}
