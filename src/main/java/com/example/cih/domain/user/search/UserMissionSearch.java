package com.example.cih.domain.user.search;

import com.example.cih.domain.user.UserMission;
import com.example.cih.dto.user.UserMissionReqDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserMissionSearch {
    Page<UserMission> searchUserMission(String[] types, String keyword, Pageable pageable, UserMissionReqDTO userMissionReqDTO);


}
