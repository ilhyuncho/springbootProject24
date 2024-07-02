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
import com.example.cih.dto.BuyingCarListResDTO;
import com.example.cih.dto.PageRequestDTO;
import com.example.cih.dto.buyingCar.BuyingCarRegDTO;
import com.example.cih.dto.buyingCar.BuyingCarViewDTO;
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

//    @Override
//    public List<BuyingCarViewDTO> getListBuyingCar(Long sellingCarId) {
//
//        SellingCar sellingCar = sellingCarRepository.findById(sellingCarId)
//                .orElseThrow(() -> new NoSuchElementException("해당 차 판매 정보가 존재하지않습니다"));
//
//        List<BuyingCar> ListBuyingCar = buyingCarRepository.findBySellingCar(sellingCar);
//
//        List<BuyingCarViewDTO> ListBuyingCarViewDTO = ListBuyingCar.stream()
//                .map(BuyingCarServiceImpl::entityToDTO)
//                .sorted(Comparator.comparing(BuyingCarViewDTO::getProposalPrice).reversed())   // 제안 가격 내림차순으로 정렬
//                .collect(Collectors.toList());
//
//        return ListBuyingCarViewDTO;
//    }

    @Override
    public BuyingCarListResDTO<BuyingCarViewDTO> getListBuyingCarInfo(PageRequestDTO pageRequestDTO, Long sellingCarId) {

        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable("proposalPrice");

        Page<BuyingCarViewDTO> resultDTO = buyingCarRepository.getBuyingCarInfo(sellingCarId, pageable);
        List<BuyingCarViewDTO> listBuyingCarViewDTO = resultDTO.getContent();

        log.error("sellingCarId : " + sellingCarId);
        listBuyingCarViewDTO.forEach(log::error);

        int maxProposalPrice = 0;
        if( listBuyingCarViewDTO.size() > 0){
            // stream max, Comparator 활용
            maxProposalPrice = listBuyingCarViewDTO.stream()
                    .max(Comparator.comparingInt(BuyingCarViewDTO::getProposalPrice)).get().getProposalPrice();
        }
        return new BuyingCarListResDTO<BuyingCarViewDTO>(
                pageRequestDTO, listBuyingCarViewDTO,
               (int)resultDTO.getTotalElements(),maxProposalPrice);


//        return PageResponseDTO.<BuyingCarViewDTO>withAll()
//                .pageRequestDTO(pageRequestDTO)
//                .dtoList(listBuyingCarViewDTO)
//                .total((int)resultDTO.getTotalElements())
//                .build();
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

        BuyingCar buyingCar = BuyingCar.builder()
                .proposalPrice(buyingCarRegDTO.getRequestPrice())
                .phoneNumber(buyingCarRegDTO.getPhoneNumber())
                .user(user)
                .sellingCar(sellingCar)
                .buyCarStatus(buyCarStatus)
                .build();
        buyingCarRepository.save(buyingCar);
    }
    @Override
    public void modifyBuyingCar(User user, BuyingCarRegDTO buyingCarRegDTO) {

        SellingCar sellingCar = getSellingCarInfo(buyingCarRegDTO);

        BuyingCar buyingCar = getBuyingCarInfo(sellingCar, user);

        if(buyingCar != null){
            buyingCar.changePrice(buyingCarRegDTO.getRequestPrice());
        }
        else{
            throw new OwnerCarNotFoundException("가격 제안 정보가 존재하지않습니다");
        }
    }

    @Override
    public void deleteBuyingCar(User user, BuyingCarRegDTO buyingCarRegDTO) {

        SellingCar sellingCar = getSellingCarInfo(buyingCarRegDTO);

        BuyingCar buyingCar = getBuyingCarInfo(sellingCar, user);

        if(buyingCar != null){
            buyingCarRepository.delete(buyingCar);
        }
        else{
            throw new OwnerCarNotFoundException("가격 제안 정보가 존재하지않습니다");
        }
    }

    public SellingCar getSellingCarInfo(BuyingCarRegDTO buyingCarRegDTO){

        Car car = carRepository.findById(buyingCarRegDTO.getCarId())
                .orElseThrow(() -> new OwnerCarNotFoundException("차 정보가 존재하지않습니다"));

        return sellingCarRepository.findById(car.getSellingCar().getSellingCarId())
                .orElseThrow(() -> new OwnerCarNotFoundException("차 판매 정보가 존재하지않습니다"));
    }

    public BuyingCar getBuyingCarInfo(SellingCar sellingCar, User user){    // 판매 차량을 사려고 하는 고객의 구매 정보 get

        return buyingCarRepository.findBySellingCarAndUser(sellingCar, user)
                .orElse(null);
                //.orElseThrow(() -> new OwnerCarNotFoundException("가격 제안 정보가 존재하지않습니다"));

    }

    private static BuyingCarViewDTO entityToDTO(BuyingCar buyingCar) {
        BuyingCarViewDTO buyingCarViewDTO = BuyingCarViewDTO.builder( )
                .proposalPrice(buyingCar.getProposalPrice())
                .registerDate(buyingCar.getRegisterDate())
                .userName(buyingCar.getUser().getUserName())
                .buyCarStatus(buyingCar.getBuyCarStatus())
                .carNumber(buyingCar.getSellingCar().getCar().getCarNumber())   // 너무 긴가???
                .carModel(buyingCar.getSellingCar().getCar().getCarModel())
                .carId(buyingCar.getSellingCar().getCar().getCarId())
                .build();

        return buyingCarViewDTO;
    }

}
