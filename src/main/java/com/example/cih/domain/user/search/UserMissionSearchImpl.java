package com.example.cih.domain.user.search;

import com.example.cih.common.util.Util;
import com.example.cih.domain.user.QUserMission;
import com.example.cih.domain.user.UserMission;
import com.example.cih.dto.user.UserMissionReqDTO;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.time.LocalDateTime;
import java.util.List;

@Log4j2
public class UserMissionSearchImpl extends QuerydslRepositorySupport implements UserMissionSearch {
    public UserMissionSearchImpl() {
        super(UserMission.class);
    }

    @Override
    public Page<UserMission> searchUserMission(String[] types, String keyword, Pageable pageable,
                                               UserMissionReqDTO userMissionReqDTO ) {

        log.error("searchUserMission()~~");
        LocalDateTime searchStartTime = Util.convertStringToLocalDateTime(userMissionReqDTO.getFromDay());
        LocalDateTime searchEndTime = Util.convertStringToLocalDateTime(userMissionReqDTO.getToDay());
        log.error("searchStartTime : " + searchStartTime);
        log.error("searchEndTime : " + searchEndTime);

        QUserMission userMission = QUserMission.userMission;
        JPQLQuery<UserMission> query = from(userMission);

        query.where(userMission.regDate.after(searchStartTime).and(userMission.regDate.before(searchEndTime)));

        //paging
       // this.getQuerydsl().applyPagination(pageable, query);    // MariaDB가 페이징 처리에 사용하는 limit을 적용

        List<UserMission> userMissionList = query.fetch();
        long count = query.fetchCount();

        for (UserMission mission : userMissionList) {
            log.error(mission.toString());
        }
        log.error("count:" + count);

        return new PageImpl<>(userMissionList, pageable, count);
    }



}
