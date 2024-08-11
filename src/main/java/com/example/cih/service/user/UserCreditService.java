package com.example.cih.service.user;

import com.example.cih.domain.user.User;
import com.example.cih.dto.user.UserCreditDTO;

public interface UserCreditService {

    Long register(String memberId, UserCreditDTO userCreditDTO);

    UserCreditDTO readCreditInfo(User user);
}
