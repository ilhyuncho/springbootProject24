package com.example.cih.service.buyingCar;

import com.example.cih.common.exception.OwnerCarNotFoundException;
import com.example.cih.domain.buyingCar.BuyCarStatus;
import com.example.cih.domain.buyingCar.BuyingCar;
import com.example.cih.domain.buyingCar.BuyingCarRepository;
import com.example.cih.domain.sellingCar.SellingCar;
import com.example.cih.domain.sellingCar.SellingCarRepository;
import com.example.cih.domain.user.User;
import com.example.cih.dto.buyingCar.BuyingCarListResDTO;
import com.example.cih.dto.PageRequestDTO;
import com.example.cih.dto.buyingCar.BuyingCarRegDTO;
import com.example.cih.dto.buyingCar.BuyingCarViewDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class BuyingCarServiceImpl implements BuyingCarService {
    private final SellingCarRepository sellingCarRepository;
    private final BuyingCarRepository buyingCarRepository;

    @Override
    public BuyingCar getBuyingCarInfo(User user, SellingCar sellingCar){
        // 판매 차량을 사려고 하는 고객의 구매 정보 get
        return buyingCarRepository.findBySellingCarAndUserAndIsActive(sellingCar, user, true)
                .orElse(null);
    }

    @Override
    public List<BuyingCarViewDTO> getListBuyingCarInfo(User user) {
        return buyingCarRepository.findByUser(user).stream()
                .map(BuyingCarServiceImpl::entityToDTO).collect(Collectors.toList());
    }

    private SellingCar getSellingCarInfo(Long sellingCarId){
        return sellingCarRepository.findById(sellingCarId)
                .orElseThrow(() -> new NoSuchElementException("해당 차 판매 정보가 존재하지않습니다"));
    }

    @Override
    public BuyingCarListResDTO<BuyingCarViewDTO> getPageBuyingCarInfo(PageRequestDTO pageRequestDTO, Long sellingCarId) {

        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable("proposalPrice");

        // 해당 차의 구매 요청 리스트 get
        Page<BuyingCarViewDTO> resultDTO = buyingCarRepository.getBuyingCarInfo(sellingCarId, pageable);
        List<BuyingCarViewDTO> listBuyingCarViewDTO = resultDTO.getContent();

        // 구매 희망 최고 가격 get
        int maxProposalPrice = 0;
        if(listBuyingCarViewDTO.size() > 0){
            // stream max, Comparator 활용
            maxProposalPrice = listBuyingCarViewDTO.stream()
                    .max(Comparator.comparingInt(BuyingCarViewDTO::getProposalPrice))
                    .get().getProposalPrice();
        }

        return BuyingCarListResDTO.<BuyingCarViewDTO>withSuper()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(listBuyingCarViewDTO)
                .total((int)resultDTO.getTotalElements())
                .highProposalPrice(maxProposalPrice)
                .build();
    }

    @Override
    public BuyingCarViewDTO getHighProposalBuyingCar(Long sellingCarId) {

        // 구매하려는 차량 정보
        SellingCar sellingCar = getSellingCarInfo(sellingCarId);

        BuyingCar highProposalPriceInfo = buyingCarRepository.findHighProposalPriceInfo(sellingCarId);
        if(highProposalPriceInfo != null)
        {
            BuyingCarViewDTO buyingCarViewDTO = entityToDTO(highProposalPriceInfo);
            return buyingCarViewDTO;
        }

        return null;
    }

    @Override
    public void registerBuyingCar(User user, BuyingCarRegDTO buyingCarRegDTO) {

        BuyCarStatus buyCarStatus = BuyCarStatus.fromValue(buyingCarRegDTO.getOfferType());

        // 구매하려는 차량 정보
        SellingCar sellingCar = getSellingCarInfo(buyingCarRegDTO.getSellingCarId());

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

        // 구매하려는 차량 정보
        SellingCar sellingCar = getSellingCarInfo(buyingCarRegDTO.getSellingCarId());

        // 구매 신청 내역 get
        BuyingCar buyingCar = getBuyingCarInfo(user, sellingCar);
        Objects.requireNonNull(buyingCar, () -> {   // requireNonNull은 해당 참조가 null일 경우 즉시 개발자에게 알리는 것이 목적
            throw new OwnerCarNotFoundException("구매 신청 정보가 존재 하지 않습니다"); });

        // 신청 상태 변경
        BuyCarStatus buyCarStatus = BuyCarStatus.fromValue(buyingCarRegDTO.getOfferType());
        buyingCar.changeBuyCarStatus(buyCarStatus);

        if(buyCarStatus == BuyCarStatus.PROPOSE_CHANGE_PRICE){
            buyingCar.changePrice(buyingCarRegDTO.getRequestPrice());
        }
    }

    private static BuyingCarViewDTO entityToDTO(BuyingCar buyingCar) {

        return BuyingCarViewDTO.builder( )
                .proposalPrice(buyingCar.getProposalPrice())
                .registerTime(buyingCar.getRegisterTime())
                .userName(buyingCar.getUser().getUserName())
                .buyCarStatus(buyingCar.getBuyCarStatus())
                .carNumber(buyingCar.getSellingCar().getCar().getCarNumber())
                .carModel(buyingCar.getSellingCar().getCar().getCarModel())
                .carId(buyingCar.getSellingCar().getCar().getCarId())
                .sellingCarId(buyingCar.getSellingCar().getSellingCarId())
                .build();
    }

}
