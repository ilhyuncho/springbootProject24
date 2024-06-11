package com.example.cih.service.user;

import com.example.cih.common.exception.UserNotFoundException;
import com.example.cih.domain.reference.RefMission;
import com.example.cih.domain.reference.RefMissionRepository;
import com.example.cih.domain.user.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;


@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class UserMissionServiceImpl implements UserMissionService{

    private final UserMissionRepository userMissionRepository;
    private final UserRepository userRepository;
    private final RefMissionRepository refMissionRepository;

    @Override
    public void insertUserMission(String userName, UserActionType userActionType, String...varCheckValue) {

        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new UserNotFoundException("해당 유저는 존재하지 않습니다"));

        String checkValue = Arrays.stream(varCheckValue).findFirst().orElse(null);

        RefMission refMission = checkMissionIncomplete(user, userActionType, checkValue);

        if( refMission != null){
            log.error("refMission.getRefMissionId() : " + refMission.getRefMissionId().toString());
            log.error("RefMissionType.fromValue() : " + RefMissionType.fromValue(refMission.getRefMissionId()));

            user.addMPoint(refMission.getGainPoint());

            userMissionRepository.save(UserMission.builder()
                    .user(user)
                    .refMissionType(RefMissionType.fromValue(refMission.getRefMissionId()))
                    .gainPoint(refMission.getGainPoint())
                    .checkValue(checkValue)
                    .build());
        }
    }

    @Override
    public RefMission checkMissionIncomplete(User user, UserActionType userActionType, String checkValue ) {

        List<UserMission> userMission = userMissionRepository.findByUser(user);

        RefMission refMission = null;

        if(userActionType.equals(UserActionType.ACTION_LOGIN)){
            if( userMission.size() == 0){
               
                refMission = refMissionRepository.findById(RefMissionType.FIRST_LOGIN.getType())
                        .orElseThrow(() -> new NoSuchElementException("해당 미션 정보가 존재하지않습니다"));
                log.error("최초 로그인");
            }
            else{
                long count = userMission.stream()
                        .filter(mission -> mission.getRefMissionType().equals(RefMissionType.DAILY_LOGIN)
                                || mission.getRefMissionType().equals(RefMissionType.FIRST_LOGIN))
                        .filter(mission -> mission.getRegDate().toLocalDate().equals(LocalDate.now()))
                        .count();

                if(count == 0){
                    refMission = refMissionRepository.findById(RefMissionType.DAILY_LOGIN.getType())
                            .orElseThrow(() -> new NoSuchElementException("해당 미션 정보가 존재하지않습니다"));
                    log.error("오늘 출석");
                }
            }
        }
        else if(userActionType.equals(UserActionType.ACTION_REG_MY_CAR)){

            if(checkValue == null){
                return null;
            }
            long count = userMission.stream()
                    .filter(mission -> mission.getRefMissionType().equals(RefMissionType.REGISTER_CAR))
                    .filter(mission -> mission.getCheckValue().equals(checkValue)).count();

            if(count == 0){
                refMission = refMissionRepository.findById(RefMissionType.REGISTER_CAR.getType())
                        .orElseThrow(() -> new NoSuchElementException("해당 미션 정보가 존재하지않습니다"));
            }
            else{
                log.error("이미 미션 포인트를 받은 차량 등록 임!!");
            }
        }
        else if(userActionType.equals(UserActionType.ACTION_REG_SELLING_CAR)){
            if(checkValue == null){
                return null;
            }
            long count = userMission.stream()
                    .filter(mission -> mission.getRefMissionType().equals(RefMissionType.SELL_CAR))
                    .filter(mission -> mission.getCheckValue().equals(checkValue)).count();

            if(count == 0){
                refMission = refMissionRepository.findById(RefMissionType.SELL_CAR.getType())
                        .orElseThrow(() -> new NoSuchElementException("해당 미션 정보가 존재하지않습니다"));
            }
            else{
                log.error("이미 미션 포인트를 받은 차량 판매 임!!");
            }
        }

        return refMission;
    }

    @Override
    public List<UserMission> getListUserMission(String userName) {

        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new UserNotFoundException("해당 유저는 존재하지 않습니다"));

        List<UserMission> listUserMission = userMissionRepository.findByUser(user);

        for (UserMission userMission : listUserMission) {

            if(userMission.getRefMissionType() == RefMissionType.DAILY_LOGIN){
                log.error("DAILY_LOGIN : " +  userMission.toString());
            }
            if(userMission.getRefMissionType() == RefMissionType.FIRST_LOGIN){
                log.error("FIRST_LOGIN : " +  userMission.toString());
            }
        }

        listUserMission.forEach(log::error);

        return listUserMission;
    }
}
