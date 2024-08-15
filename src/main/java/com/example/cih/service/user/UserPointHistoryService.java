package com.example.cih.service.user;

import com.example.cih.domain.reference.RefPointSituation;
import com.example.cih.domain.user.User;
import com.example.cih.domain.user.UserActionType;
import com.example.cih.dto.PageRequestDTO;
import com.example.cih.dto.user.*;


public interface UserPointHistoryService {

    void gainUserPoint(String memberId, UserActionType userActionType, String... varCheckValue);
    void consumeUserPoint(String memberId, UserActionType userActionType, int consumePoint);

    RefPointSituation checkMissionIncomplete(User user, UserActionType userActionType, String checkValue);

    UserListPointHistoryResDTO<UserPointHistoryResDTO>
        getListUserPointHistory(PageRequestDTO pageRequestDTO, User user, UserPointHistoryReqDTO userPointHistoryReqDTO);
}
