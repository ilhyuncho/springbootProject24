package com.example.cih.service.user;

import com.example.cih.domain.user.User;
import com.example.cih.dto.PageRequestDTO;
import com.example.cih.dto.PageResponseDTO;
import com.example.cih.dto.user.UserAlarmDTO;


public interface UserAlarmService {
    UserAlarmDTO getAlarmInfo(Long alarmId);

    PageResponseDTO<UserAlarmDTO> getListAlarm(PageRequestDTO pageRequestDTO, User user);

    boolean isNewAlarm(User user);

    void registerAlarm(User user, String alarmTitle, String alarmContent);
}
