package com.example.cih.service.buyingCar;

import com.example.cih.domain.buyingCar.BuyingCar;
import com.example.cih.domain.sellingCar.SellingCar;
import com.example.cih.domain.user.User;
import com.example.cih.dto.buyingCar.BuyingCarListResDTO;
import com.example.cih.dto.PageRequestDTO;
import com.example.cih.dto.buyingCar.BuyingCarRegDTO;
import com.example.cih.dto.buyingCar.BuyingCarViewDTO;

import java.util.List;

public interface BuyingCarService {
    void registerBuyingCar(User user, BuyingCarRegDTO buyingCarRegDTO);
    void updateBuyingCar(User user, BuyingCarRegDTO buyingCarRegDTO);
    List<BuyingCarViewDTO> getListBuyingCarInfo(User user);

    BuyingCarListResDTO<BuyingCarViewDTO> getListBuyingCarInfo(PageRequestDTO pageRequestDTO, Long sellingCarId);
    BuyingCarViewDTO getHighProposalBuyingCar(Long sellingCarId);

    BuyingCar getBuyingCarInfo(SellingCar sellingCar, User user);
}
