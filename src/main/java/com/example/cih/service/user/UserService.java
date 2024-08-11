package com.example.cih.service.user;

import com.example.cih.domain.user.User;
import com.example.cih.dto.member.MemberJoinDTO;
import com.example.cih.dto.user.UserAddressReqDTO;
import com.example.cih.dto.user.UserDTO;
import com.example.cih.dto.user.UserPasswordReqDTO;

public interface UserService {
    User findUser(String memberId);
    UserDTO findUserDTO(String memberId);
    Long registerUser(MemberJoinDTO memberJoinDTO);
    User registerMainAddress(String memberId, UserAddressReqDTO userAddressReqDTO);
    void changePassword(String memberId, UserPasswordReqDTO userPasswordReqDTO);
}
