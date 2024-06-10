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
    public void insertUserMission(String userName, RefMissionType refMissionType) {

        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new UserNotFoundException("해당 유저는 존재하지 않습니다"));

        List<UserMission> userMission = userMissionRepository.findByUser(user);

        RefMission refMission = null;
        if( userMission.size() == 0){
            // 최초 로그인
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

        if( refMission != null){
            user.addMPoint(refMission.getGainPoint());

            log.error("refMission.getRefMissionId() : " + refMission.getRefMissionId().toString());
            log.error("RefMissionType.fromValue() : " + RefMissionType.fromValue(refMission.getRefMissionId()));

            userMissionRepository.save(UserMission.builder()
                    .user(user)
                    .refMissionType(RefMissionType.fromValue(refMission.getRefMissionId()))
                    .gainPoint(refMission.getGainPoint())
                    .build());
        }
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
