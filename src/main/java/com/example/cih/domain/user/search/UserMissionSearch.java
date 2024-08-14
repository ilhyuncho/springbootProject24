package com.example.cih.domain.user.search;

import com.example.cih.domain.user.RefMissionType;
import com.example.cih.domain.user.User;
import com.example.cih.domain.user.UserMission;
import com.example.cih.dto.user.UserMissionReqDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserMissionSearch {
    Page<UserMission> searchUserMission(String[] types, String keyword, Pageable pageable, UserMissionReqDTO userMissionReqDTO);
    List<UserMission> getListUserMissionByActionType(User user, RefMissionType refMissionType, String checkValue);


}
