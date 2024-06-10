package com.example.cih.service.user;

import com.example.cih.domain.user.RefMissionType;
import com.example.cih.domain.user.UserMission;

import java.util.List;

public interface UserMissionService {

    void insertUserMission(String userName, RefMissionType refMissionType);
    List<UserMission> getListUserMission(String userName);
}
