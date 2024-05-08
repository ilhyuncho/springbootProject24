package com.example.cih.service.buyingCar;

import com.example.cih.dto.buyingCar.BuyingCarRegDTO;
import com.example.cih.dto.buyingCar.BuyingCarViewDTO;

import java.util.List;

public interface BuyingCarService {
    void registerBuyingCar(String userName, BuyingCarRegDTO buyingCarRegDTO);

    List<BuyingCarViewDTO> getListBuyingCar(Long sellingCarId);
}
