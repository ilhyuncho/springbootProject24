package com.example.cih.service.user;

import com.example.cih.domain.user.User;
import com.example.cih.dto.user.UserDTO;

public interface UserService {

    Long registerUser(String userName);
    UserDTO findUserDTO(String userName);
    User findUser(String userName);
}
