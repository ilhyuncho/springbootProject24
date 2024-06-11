package com.example.cih.service.user;

import com.example.cih.domain.reference.RefMission;
import com.example.cih.domain.user.User;
import com.example.cih.domain.user.UserActionType;
import com.example.cih.domain.user.UserMission;

import java.util.List;

public interface UserMissionService {

    void insertUserMission(String userName, UserActionType userActionType, String...varCheckValue);

    RefMission checkMissionIncomplete(User user, UserActionType userActionType, String checkValue);

    List<UserMission> getListUserMission(String userName);
}
