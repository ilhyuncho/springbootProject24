package com.example.cih.service.buyingCar;

import com.example.cih.domain.user.User;
import com.example.cih.dto.PageRequestDTO;
import com.example.cih.dto.PageResponseDTO;
import com.example.cih.dto.buyingCar.BuyingCarRegDTO;
import com.example.cih.dto.buyingCar.BuyingCarViewDTO;

import java.util.List;

public interface BuyingCarService {
    void registerBuyingCar(User user, BuyingCarRegDTO buyingCarRegDTO);
    void modifyBuyingCar(User user, BuyingCarRegDTO buyingCarRegDTO);
    void deleteBuyingCar(User user, BuyingCarRegDTO buyingCarRegDTO);
    List<BuyingCarViewDTO> getListBuyingCar(Long sellingCarId);

    PageResponseDTO<BuyingCarViewDTO>  getListBuyingCarInfo(PageRequestDTO pageRequestDTO, Long sellingCarId);
    BuyingCarViewDTO getHighProposalBuyingCar(Long sellingCarId);
}
