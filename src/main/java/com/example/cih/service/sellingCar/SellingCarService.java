package com.example.cih.service.sellingCar;

import com.example.cih.dto.PageRequestDTO;
import com.example.cih.dto.PageResponseDTO;
import com.example.cih.dto.sellingCar.SellingCarRegDTO;
import com.example.cih.dto.sellingCar.SellingCarViewDTO;

import java.util.List;

public interface SellingCarService {
    void registerSellingCar(String userName, SellingCarRegDTO sellingCarRegDTO);
    SellingCarViewDTO getSellingCar(Long sellingCarId);
    PageResponseDTO<SellingCarViewDTO> getListSellingCar(PageRequestDTO pageRequestDTO);

    void cancelSellingCar(String userName, Long carId);
}
