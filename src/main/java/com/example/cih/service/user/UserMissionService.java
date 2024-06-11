package com.example.cih.service.user;

import com.example.cih.domain.reference.RefMission;
import com.example.cih.domain.user.User;
import com.example.cih.domain.user.UserActionType;
import com.example.cih.dto.user.UserMissionResDTO;

import java.util.List;

public interface UserMissionService {

    void insertUserMission(String userName, UserActionType userActionType, String...varCheckValue);

    RefMission checkMissionIncomplete(User user, UserActionType userActionType, String checkValue);

    List<UserMissionResDTO> getListUserMission(String userName);
}
