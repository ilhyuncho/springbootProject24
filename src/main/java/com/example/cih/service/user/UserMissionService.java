package com.example.cih.service.user;

import com.example.cih.domain.reference.RefMission;
import com.example.cih.domain.user.User;
import com.example.cih.domain.user.UserActionType;
import com.example.cih.dto.PageRequestDTO;
import com.example.cih.dto.user.UserMissionListResDTO;
import com.example.cih.dto.user.UserMissionReqDTO;
import com.example.cih.dto.user.UserMissionResDTO;


public interface UserMissionService {

    void insertUserMission(String userName, UserActionType userActionType, String... varCheckValue);

    RefMission checkMissionIncomplete(User user, UserActionType userActionType, String checkValue);

    UserMissionListResDTO<UserMissionResDTO> getListUserMission(PageRequestDTO pageRequestDTO, User user, UserMissionReqDTO userMissionReqDTO);
}
