package com.example.cih.service.user;

import com.example.cih.domain.user.*;
import com.example.cih.dto.PageRequestDTO;
import com.example.cih.dto.PageResponseDTO;
import com.example.cih.dto.user.UserAlarmDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class UserAlarmServiceImpl implements UserAlarmService {

    private final UserAlarmRepository userAlarmRepository;

    @Override
    public PageResponseDTO<UserAlarmDTO> getAlarmInfo(PageRequestDTO pageRequestDTO, User user){

        Pageable pageable = pageRequestDTO.getPageable("userAlarmId");

        Page<UserAlarm> result = userAlarmRepository.findByUser(user, pageable);
        List<UserAlarm> listAlarm = result.getContent();

        List<UserAlarmDTO> listAlarmDTO = listAlarm.stream()
                .map(UserAlarmServiceImpl::entityToDTO)
                .collect(Collectors.toList());

        listAlarm.forEach(log::error);

        return PageResponseDTO.<UserAlarmDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(listAlarmDTO)
                .total((int)result.getTotalElements())
                .build();
    }

    private static UserAlarmDTO entityToDTO(UserAlarm userAlarm) {
        return UserAlarmDTO.builder()
                .userAlarmID(userAlarm.getUserAlarmId())
                .alarmTitle(userAlarm.getAlarmTitle())
                .alarmContent(userAlarm.getAlarmContent())
                .regDate(userAlarm.getRegDate())
                .build();
    }


}
