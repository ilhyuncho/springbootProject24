package com.example.cih.service.sellingCar;

import com.example.cih.domain.user.User;
import com.example.cih.dto.PageRequestDTO;
import com.example.cih.dto.PageResponseDTO;
import com.example.cih.dto.sellingCar.SellingCarRegDTO;
import com.example.cih.dto.sellingCar.SellingCarResDTO;

import java.util.List;

public interface SellingCarService {
    void registerSellingCar(String userName, SellingCarRegDTO sellingCarRegDTO);
    SellingCarResDTO getSellingCarInfo(Long sellingCarId, User user);
    PageResponseDTO<SellingCarResDTO> getListSellingCar(PageRequestDTO pageRequestDTO);
    List<SellingCarResDTO> getRecommendList();

    void cancelSellingCar(String userName, Long carId);
}
