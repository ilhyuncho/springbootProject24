package com.example.cih.domain.notification.search;

import com.example.cih.domain.notification.EventNotification;
import com.example.cih.domain.notification.QEventNotification;
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
import java.util.Optional;
import java.util.Random;

@Log4j2
public class EventNotificationSearchImpl extends QuerydslRepositorySupport implements EventNotificationSearch {
    public EventNotificationSearchImpl() {
        super(EventNotification.class);
    }


    @Override
    public Page<EventNotification> searchAll(String[] types, String keyword, Pageable pageable) {

        QEventNotification eventNotification = QEventNotification.eventNotification;
        JPQLQuery<EventNotification> query = from(eventNotification);

        if (types != null && types.length > 0) {
            BooleanBuilder booleanBuilder = new BooleanBuilder();
            for (String type : types) {
                switch (type) {
                    case "u":
                        booleanBuilder.or(eventNotification.isUse.eq(true));
                        break;
                }
            }
            query.where(booleanBuilder);
        }
        //query.where(newsNotification.sellingCarStatus.eq(SellingCarStatus.PROCESSING));

        this.getQuerydsl().applyPagination(pageable, query);
        List<EventNotification> list = query.fetch();
        long count = query.fetchCount();

        for (EventNotification event : list) {
            log.error(event.toString());
        }

        return new PageImpl<>(list, pageable, count);
    }

    @Override
    public EventNotification searchTodayRandomEvent() {

        QEventNotification eventNotification = QEventNotification.eventNotification;
        JPQLQuery<EventNotification> query = from(eventNotification);

        LocalDate nowDate = LocalDate.now();

        query.where(eventNotification.isUse.eq(true).and(eventNotification.isPopup.eq(true)));
        // loe : 작거나 같음 , goe : 크거나 같음
        query.where(eventNotification.eventStartDate.loe(nowDate).and(eventNotification.eventEndDate.goe(nowDate)));

        List<EventNotification> list = query.fetch();
        if( list.size() > 0){
            // 해당 이벤트 중 랜덤으로 하나 결정
            int skipIndex = new Random().nextInt(list.size());

            Optional<EventNotification> result = list
                    .stream()
                    .skip(skipIndex)
                    .findFirst();

            return result.orElse(null);
        }
        return null;
    }


}
