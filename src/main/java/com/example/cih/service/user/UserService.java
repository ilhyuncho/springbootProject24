package com.example.cih.service.user;

import com.example.cih.domain.user.User;
import com.example.cih.dto.member.MemberJoinDTO;
import com.example.cih.dto.user.UserAddressReqDTO;
import com.example.cih.dto.user.UserDTO;
import com.example.cih.dto.user.UserPasswordReqDTO;

public interface UserService {

    Long registerUser(MemberJoinDTO memberJoinDTO);
    UserDTO findUserDTO(String memberId);
    User findUser(String memberId);
    User registerMainAddress(String memberId, UserAddressReqDTO userAddressReqDTO);
    void changePassword(String memberId, UserPasswordReqDTO userPasswordReqDTO);
}
