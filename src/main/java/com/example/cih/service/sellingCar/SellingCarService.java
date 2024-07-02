package com.example.cih.service.sellingCar;

import com.example.cih.domain.user.User;
import com.example.cih.dto.PageRequestDTO;
import com.example.cih.dto.PageResponseDTO;
import com.example.cih.dto.sellingCar.SellingCarRegDTO;
import com.example.cih.dto.sellingCar.SellingCarViewDTO;

import java.util.List;

public interface SellingCarService {
    void registerSellingCar(String userName, SellingCarRegDTO sellingCarRegDTO);
    SellingCarViewDTO getSellingCarInfo(Long sellingCarId, User user);
    PageResponseDTO<SellingCarViewDTO> getListSellingCar(PageRequestDTO pageRequestDTO);
    List<SellingCarViewDTO> getRecommendList();

    void cancelSellingCar(String userName, Long carId);
}
