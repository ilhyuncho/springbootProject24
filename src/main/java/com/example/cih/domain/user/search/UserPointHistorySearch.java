package com.example.cih.domain.user.search;

import com.example.cih.domain.user.*;
import com.example.cih.dto.user.UserPointHistoryReqDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserPointHistorySearch {
    Page<UserPointHistory> searchUserPointHistory(String[] types, String keyword, Pageable pageable, UserPointHistoryReqDTO userPointHistoryReqDTO);
    List<UserPointHistory> getListPointHistoryBySituationType(User user, PointSituation pointSituation, String checkValue);


}
