package com.example.cih.service.user;

import com.example.cih.dto.user.UserDTO;

public interface UserService {

    UserDTO findByUserName(String userName);
}
