package com.example.cih.service.user;

import com.example.cih.domain.user.User;
import com.example.cih.dto.PageRequestDTO;
import com.example.cih.dto.PageResponseDTO;
import com.example.cih.dto.user.UserAlarmDTO;


public interface UserAlarmService {
    PageResponseDTO<UserAlarmDTO> getAlarmInfo(PageRequestDTO pageRequestDTO, User user);

    void registerAlarm(User user, String alarmTitle, String alarmContent);
}
