package com.example.cih.service.user;

import com.example.cih.domain.user.User;
import com.example.cih.dto.user.UserAddressReqDTO;
import com.example.cih.dto.user.UserDTO;
import com.example.cih.dto.user.UserPasswordReqDTO;

public interface UserService {

    Long registerUser(String userName);
    UserDTO findUserDTO(String userName);
    User findUser(String userName);
    User registerMainAddress(String userName, UserAddressReqDTO userAddressReqDTO);
    void changePassword(String userName, UserPasswordReqDTO userPasswordReqDTO);
}
