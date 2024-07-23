package com.example.cih.service.user;

import com.example.cih.domain.sellingCar.SellingCar;
import com.example.cih.domain.user.User;
import com.example.cih.dto.sellingCar.SellingCarResDTO;

import java.util.List;


public interface UserSearchCarHistoryService {

    void insertSearchCarHistory(User user, SellingCar sellingCar);

    List<SellingCarResDTO> getSearchCarHistory(User user);
}
