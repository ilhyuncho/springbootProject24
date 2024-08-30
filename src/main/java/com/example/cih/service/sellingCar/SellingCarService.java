package com.example.cih.service.sellingCar;

import com.example.cih.domain.user.User;
import com.example.cih.dto.PageRequestDTO;
import com.example.cih.dto.PageRequestExtDTO;
import com.example.cih.dto.PageResponseDTO;
import com.example.cih.dto.sellingCar.SellingCarRegDTO;
import com.example.cih.dto.sellingCar.SellingCarResDTO;

import java.util.List;

public interface SellingCarService {

    SellingCarResDTO getSellingCarInfo( User user, Long sellingCarId);
    PageResponseDTO<SellingCarResDTO> getListSellingCar(PageRequestExtDTO pageRequestExtDT);
    List<SellingCarResDTO> getListRecommend();

    void registerSellingCar(User user, SellingCarRegDTO sellingCarRegDTO);
    void likeSellingCar(User user, SellingCarRegDTO sellingCarRegDTO);
    void updateSellingCar(SellingCarRegDTO sellingCarRegDTO);
}
