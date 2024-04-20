package com.example.cih.service.user;

import com.example.cih.common.exception.ItemNotFoundException;
import com.example.cih.common.exception.UserCreditNotFoundException;
import com.example.cih.domain.user.User;
import com.example.cih.domain.user.UserCredit;
import com.example.cih.domain.user.UserCreditRepository;
import com.example.cih.domain.user.UserRepository;
import com.example.cih.dto.user.UserCreditDTO;
import com.example.cih.service.shop.ShopItemService;
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

    private final UserService userService;

    @Override
    public Long register(String userName, UserCreditDTO userCreditDTO){

        // 고객 정보 get
        User user = userService.findUser(userName);

        UserCredit userCredit = UserCredit.builder()
                .user(user)
                .bankName(userCreditDTO.getBankName())
                .bankAccount(userCreditDTO.getBankAccount())
                .build();

        Long userCreditsId = userCreditRepository.save(userCredit).getUserCreditsId();

        return userCreditsId;
    }
    @Override
    public UserCreditDTO readCreditInfo(User user){

        //UserCredit userCredit = userCreditRepository.findByUser(user).
               // .orElseThrow(() -> new UserCreditNotFoundException("결제 정보가 존재하지않습니다"));

        Optional<UserCredit> result = userCreditRepository.findByUser(user);

        if( result.isPresent()){
            UserCredit userCredit = result.get();

            log.error("readCreditInfo() - userCredit=" + userCredit);

            UserCreditDTO userCreditDTO = UserCreditDTO.builder()
                    .userCreditID(userCredit.getUserCreditsId())
                    .userId(userCredit.getUser().getUserId())
                    .bankAccount(userCredit.getBankAccount())
                    .bankName(userCredit.getBankName())
                    .build();

            return userCreditDTO;
        }

        return null;
    }

}
