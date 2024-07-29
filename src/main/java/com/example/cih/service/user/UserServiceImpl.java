package com.example.cih.service.user;

import com.example.cih.common.exception.UserNotFoundException;
import com.example.cih.domain.user.User;
import com.example.cih.domain.user.UserGradeType;
import com.example.cih.domain.user.UserRepository;
import com.example.cih.dto.user.UserAddressReqDTO;
import com.example.cih.dto.user.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Override
    public Long registerUser(String userName) {
        User user = User.builder()
                .userName(userName)
                .mPoint(0)
                .mGrade(UserGradeType.GRADE_A)
                .build();

        return userRepository.save(user).getUserId();
    }

    @Override
    public UserDTO findUserDTO(String userName){
        User user = userRepository.findByUserName(userName)
                .orElseThrow(()-> new UserNotFoundException("해당 유저는 존재하지 않습니다"));

        return entityToDTO(user);
    }

    @Override
    public User findUser(String userName) {
        return userRepository.findByUserName(userName)
                .orElseThrow(()-> new UserNotFoundException("해당 유저는 존재하지 않습니다"));
    }

    @Override
    public User registerMainAddress(String userName, UserAddressReqDTO userAddressReqDTO) {

        User user = findUser(userName);

        user.registerMainAddress(userAddressReqDTO);

        return user;
    }


    private static UserDTO entityToDTO(User user) {

        return UserDTO.builder()
                .userID(user.getUserId())
                .userName(user.getUserName())
                .address(user.getAddress() != null ? user.getAddress().fullAddress() : null)
                .billingAddress(user.getBillingAddress() != null ? user.getBillingAddress().fullAddress() : null)
                .mPoint(user.getMPoint())
                .mGrade(user.getMGrade())
                .build();
    }

}
