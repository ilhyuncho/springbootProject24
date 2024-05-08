package com.example.cih.service.sellingCar;

import com.example.cih.dto.sellingCar.BuyRequestRegDTO;
import com.example.cih.dto.sellingCar.BuyRequestViewDTO;

import java.util.List;

public interface BuyRequestService {
    void registerBuyRequest(String userName, BuyRequestRegDTO buyRequestRegDTO);

    List<BuyRequestViewDTO> getListBuyRequest(Long sellingCarId);
}
