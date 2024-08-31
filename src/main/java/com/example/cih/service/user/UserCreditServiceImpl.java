package com.example.cih.service.user;

import com.example.cih.domain.user.User;
import com.example.cih.domain.user.UserCredit;
import com.example.cih.domain.user.UserCreditRepository;
import com.example.cih.dto.user.UserCreditDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;


@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class UserCreditServiceImpl implements UserCreditService {

    private final UserCreditRepository userCreditRepository;

    @Override
    public Long register(User user, UserCreditDTO userCreditDTO){

        UserCredit userCredit = UserCredit.builder()
                .user(user)
                .bankName(userCreditDTO.getBankName())
                .bankAccount(userCreditDTO.getBankAccount())
                .build();

        return userCreditRepository.save(userCredit).getUserCreditId();
    }
    @Override
    public UserCreditDTO readCreditInfo(User user){

        Optional<UserCredit> result = userCreditRepository.findByUser(user);

        if(result.isPresent()){
            UserCredit userCredit = result.get();

            return UserCreditDTO.builder()
                    .userCreditID(userCredit.getUserCreditId())
                    .userId(userCredit.getUser().getUserId())
                    .bankAccount(userCredit.getBankAccount())
                    .bankName(userCredit.getBankName())
                    .build();
        }

       return new UserCreditDTO();
    }

}
