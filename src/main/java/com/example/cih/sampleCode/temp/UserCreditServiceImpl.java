package com.example.cih.sampleCode.temp;

import com.example.cih.domain.user.User;
import com.example.cih.domain.user.UserRepository;
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

        Optional<UserCredit> byUser = userCreditRepository.findByUser(user);

        UserCredit userCredit = byUser.orElseThrow();

        log.error("readCreditInfo() - userCredit=" + userCredit);


        UserCreditDTO userCreditDTO = UserCreditDTO.builder()
                .userCreditID(userCredit.getUserCreditsId())
                .userId(userCredit.getUser().getUserId())
                .bankAccount(userCredit.getBankAccount())
                .bankName(userCredit.getBankName())
                .build();

        return userCreditDTO;
    }

}
