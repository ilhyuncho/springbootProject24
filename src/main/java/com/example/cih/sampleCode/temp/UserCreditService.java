package com.example.cih.sampleCode.temp;

import com.example.cih.domain.user.User;
import com.example.cih.dto.car.CarSpecDTO;

public interface UserCreditService {

    Long register(String userName, UserCreditDTO userCreditDTO);

    UserCreditDTO readCreditInfo(User user);
}
