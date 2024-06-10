package com.example.cih.service.user;

import com.example.cih.common.exception.UserNotFoundException;
import com.example.cih.domain.user.User;
import com.example.cih.domain.user.UserRepository;
import com.example.cih.dto.user.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;


@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public UserDTO findUserDTO(String userName){
        User user = userRepository.findByUserName(userName)
                .orElseThrow(()-> new UserNotFoundException("해당 유저는 존재하지 않습니다"));

        UserDTO userDTO = entityToDTO(user);
        return userDTO;
    }

    @Override
    public User findUser(String userName) {
        User user = userRepository.findByUserName(userName)
                .orElseThrow(()-> new UserNotFoundException("해당 유저는 존재하지 않습니다"));

        return user;
    }

    private static UserDTO entityToDTO(User user) {
        UserDTO userDTO = UserDTO.builder()
                .userID(user.getUserId())
                .userName(user.getUserName())
                .address(user.getAddress().fullAddress())
                .billingAddress(user.getBillingAddress().fullAddress())
                .mPoint(user.getMPoint())
                .mGrade(user.getMGrade())
                .build();

        return userDTO;
    }

}
