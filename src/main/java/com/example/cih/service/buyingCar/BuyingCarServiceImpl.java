package com.example.cih.service.buyingCar;

import com.example.cih.common.exception.OwnerCarNotFoundException;
import com.example.cih.domain.buyingCar.BuyCarStatus;
import com.example.cih.domain.buyingCar.BuyingCar;
import com.example.cih.domain.buyingCar.BuyingCarRepository;
import com.example.cih.domain.car.Car;
import com.example.cih.domain.car.CarRepository;
import com.example.cih.domain.sellingCar.SellingCar;
import com.example.cih.domain.sellingCar.SellingCarRepository;
import com.example.cih.domain.user.User;
import com.example.cih.dto.buyingCar.BuyingCarListResDTO;
import com.example.cih.dto.PageRequestDTO;
import com.example.cih.dto.buyingCar.BuyingCarRegDTO;
import com.example.cih.dto.buyingCar.BuyingCarViewDTO;
import com.example.cih.service.car.CarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final CarRepository carRepository;
    private final CarService carService;

    @Override
    public BuyingCarListResDTO<BuyingCarViewDTO> getPageBuyingCarInfo(PageRequestDTO pageRequestDTO, Long sellingCarId) {

        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable("proposalPrice");

        Page<BuyingCarViewDTO> resultDTO = buyingCarRepository.getBuyingCarInfo(sellingCarId, pageable);
        List<BuyingCarViewDTO> listBuyingCarViewDTO = resultDTO.getContent();

        int maxProposalPrice = 0;
        if(listBuyingCarViewDTO.size() > 0){
            // stream max, Comparator 활용
            maxProposalPrice = listBuyingCarViewDTO.stream()
                    .max(Comparator.comparingInt(BuyingCarViewDTO::getProposalPrice))
                    .get().getProposalPrice();
        }

        return new BuyingCarListResDTO<BuyingCarViewDTO>(
                pageRequestDTO, listBuyingCarViewDTO,
               (int)resultDTO.getTotalElements(), maxProposalPrice);
    }

    @Override
    public BuyingCarViewDTO getHighProposalBuyingCar(Long sellingCarId) {

        SellingCar sellingCar = sellingCarRepository.findById(sellingCarId)
                .orElseThrow(() -> new NoSuchElementException("해당 차 판매 정보가 존재하지않습니다"));

        BuyingCar highProposalPriceInfo = buyingCarRepository.findHighProposalPriceInfo(sellingCarId);
        if(highProposalPriceInfo != null)
        {
            BuyingCarViewDTO buyingCarViewDTO = entityToDTO(highProposalPriceInfo);
            return buyingCarViewDTO;
        }

        return null;
    }

    @Override
    public List<BuyingCarViewDTO> getListBuyingCarInfo(User user) {

        List<BuyingCarViewDTO> listBuyingCarViewDTO = buyingCarRepository.findByUser(user).stream()
                .map(BuyingCarServiceImpl::entityToDTO).collect(Collectors.toList());

        return listBuyingCarViewDTO;
    }

    @Override
    public void registerBuyingCar(User user, BuyingCarRegDTO buyingCarRegDTO) {

        BuyCarStatus buyCarStatus = BuyCarStatus.fromValue(buyingCarRegDTO.getOfferType());

        // 구매하려는 차량 정보
        SellingCar sellingCar = getSellingCarInfo(buyingCarRegDTO);

        if(getBuyingCarInfo(user, sellingCar) != null){
            throw new OwnerCarNotFoundException("이미 구매 신청 정보가 존재합니다");
        }

        BuyingCar buyingCar = BuyingCar.builder()
                .proposalPrice(buyingCarRegDTO.getRequestPrice())
                .phoneNumber(buyingCarRegDTO.getPhoneNumber())
                .user(user)
                .sellingCar(sellingCar)
                .buyCarStatus(buyCarStatus)
                .isActive(true)
                .build();
        buyingCarRepository.save(buyingCar);
    }
    @Override
    public void updateBuyingCar(User user, BuyingCarRegDTO buyingCarRegDTO) {

        SellingCar sellingCar = getSellingCarInfo(buyingCarRegDTO);

        BuyingCar buyingCar = getBuyingCarInfo(user, sellingCar);
        if(buyingCar == null){
            throw new OwnerCarNotFoundException("구매 신청 정보가 존재 하지 않습니다");
        }

        BuyCarStatus buyCarStatus = BuyCarStatus.fromValue(buyingCarRegDTO.getOfferType());

        buyingCar.changeBuyCarStatus(buyCarStatus);

        if(buyCarStatus == BuyCarStatus.PROPOSE_CHANGE_PRICE){
            buyingCar.changePrice(buyingCarRegDTO.getRequestPrice());
        }
    }

    public SellingCar getSellingCarInfo(BuyingCarRegDTO buyingCarRegDTO){

        Car car = carService.getCarInfo(buyingCarRegDTO.getCarId());

        return sellingCarRepository.findById(car.getSellingCar().getSellingCarId())
                .orElseThrow(() -> new OwnerCarNotFoundException("차 판매 정보가 존재하지않습니다"));
    }

    public BuyingCar getBuyingCarInfo(User user, SellingCar sellingCar){    // 판매 차량을 사려고 하는 고객의 구매 정보 get

        return buyingCarRepository.findBySellingCarAndUserAndIsActive(sellingCar, user, true)
                .orElse(null);
    }

    private static BuyingCarViewDTO entityToDTO(BuyingCar buyingCar) {
        BuyingCarViewDTO buyingCarViewDTO = BuyingCarViewDTO.builder( )
                .proposalPrice(buyingCar.getProposalPrice())
                .registerTime(buyingCar.getRegisterTime())
                .userName(buyingCar.getUser().getUserName())
                .buyCarStatus(buyingCar.getBuyCarStatus())
                .carNumber(buyingCar.getSellingCar().getCar().getCarNumber())
                .carModel(buyingCar.getSellingCar().getCar().getCarModel())
                .carId(buyingCar.getSellingCar().getCar().getCarId())
                .build();

        return buyingCarViewDTO;
    }

}
