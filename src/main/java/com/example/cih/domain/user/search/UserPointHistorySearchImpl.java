package com.example.cih.domain.user.search;

import com.example.cih.common.util.Util;
import com.example.cih.domain.user.*;
import com.example.cih.dto.user.UserPointHistoryReqDTO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Log4j2
public class UserPointHistorySearchImpl extends QuerydslRepositorySupport implements UserPointHistorySearch {
    public UserPointHistorySearchImpl() {
        super(UserPointHistorySearch.class);
    }

    @Override
    public Page<UserPointHistory> searchUserPointHistory(String[] types, String keyword, Pageable pageable,
                                                         UserPointHistoryReqDTO userPointHistoryReqDTO ) {

        LocalDateTime searchStartTime = Util.convertStringToLocalDateTime(userPointHistoryReqDTO.getFromDay());
        LocalDateTime searchEndTime = Util.convertStringToLocalDateTime(userPointHistoryReqDTO.getToDay()).plusDays(1);

        log.error("searchStartTime : " + searchStartTime);
        log.error("searchEndTime : " + searchEndTime);

        QUserPointHistory userPointHistory = QUserPointHistory.userPointHistory;
        JPQLQuery<UserPointHistory> query = from(userPointHistory);

        query.where(userPointHistory.regDate.after(searchStartTime).and(userPointHistory.regDate.before(searchEndTime)));

        // 포인트 소비 or 획득 or 모두
        if (types != null && types.length > 0) {
            BooleanBuilder booleanBuilder = new BooleanBuilder();
            for (String type : types) {
                switch (type) {
                    case "g":
                        booleanBuilder.or(userPointHistory.pointType.eq(PointType.GAIN));
                        break;
                    case "c":
                        booleanBuilder.or(userPointHistory.pointType.eq(PointType.CONSUME));
                        break;
                }
            }
            query.where(booleanBuilder);
        }


        query.orderBy(userPointHistory.regDate.desc());

        //paging
        //this.getQuerydsl().applyPagination(pageable, query);    // MariaDB가 페이징 처리에 사용하는 limit을 적용

        List<UserPointHistory> listUserPointHistory = query.fetch();
        long count = query.fetchCount();

        for (UserPointHistory pointHistory : listUserPointHistory) {
            log.error(pointHistory.toString());
        }
        log.error("count:" + count);

        return new PageImpl<>(listUserPointHistory, pageable, count);
    }

    @Override
    public List<UserPointHistory> getListPointHistoryBySituationType(User user, PointSituation pointSituation, String checkValue) {

        QUserPointHistory userPointHistory = QUserPointHistory.userPointHistory;
        JPQLQuery<UserPointHistory> query = from(userPointHistory);

        query.where(userPointHistory.user.eq(user));
        query.where(userPointHistory.pointSituation.eq(pointSituation));

        if(pointSituation.equals(PointSituation.DAILY_LOGIN)){
            LocalDate now = LocalDate.now();

            LocalDateTime start = LocalDateTime.of(now.getYear(),now.getMonth(),now.getDayOfMonth(),0,0,0);
            LocalDateTime end = LocalDateTime.of(now.getYear(),now.getMonth(),now.getDayOfMonth(),23,59,59);

            query.where(userPointHistory.regDate.between(start, end));
        }
        else if(pointSituation.equals(PointSituation.REGISTER_CAR) || pointSituation.equals(PointSituation.SELL_CAR)){
            query.where(userPointHistory.checkValue.eq(checkValue));
        }

        List<UserPointHistory> listUserPointHistory = query.fetch();
        long count = query.fetchCount();

        //userMissionList.forEach(log::error);

        return listUserPointHistory;

    }

}
