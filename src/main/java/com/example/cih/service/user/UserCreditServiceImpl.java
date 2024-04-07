package com.example.cih.service.user;

import com.example.cih.domain.user.User;
import com.example.cih.domain.user.UserCredit;
import com.example.cih.domain.user.UserCreditRepository;
import com.example.cih.domain.user.UserRepository;
import com.example.cih.dto.user.UserCreditDTO;
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
public class UserCreditServiceImpl implements UserCreditService {

    private final UserCreditRepository userCreditRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public Long register(String userName, UserCreditDTO userCreditDTO){

        // 고객 정보 get
        Optional<User> user = userRepository.findByUserName(userName);
        User userInfo = user.orElseThrow();

        log.error("userInfo: " + userInfo);

        UserCredit userCredit = UserCredit.builder()
                .user(userInfo)
                .bankName(userCreditDTO.getBankName())
                .bankAccount(userCreditDTO.getBankAccount())
                .build();

        Long userCreditsId = userCreditRepository.save(userCredit).getUserCreditsId();

        return userCreditsId;
    }
    @Override
    public UserCreditDTO readCreditInfo(User user){

        UserCreditDTO userCreditDTO = null;

        Optional<UserCredit> byUser = userCreditRepository.findByUser(user);

        if(byUser.isPresent()) {
            UserCredit userCredit = byUser.get();

            log.error("readCreditInfo() - userCredit=" + userCredit);

            userCreditDTO = UserCreditDTO.builder()
                    .userCreditID(userCredit.getUserCreditsId())
                    .userId(userCredit.getUser().getUserId())
                    .bankAccount(userCredit.getBankAccount())
                    .bankName(userCredit.getBankName())
                    .build();
        }


        return userCreditDTO;
    }

}
