package com.example.cih.domain.notification.search;

import com.example.cih.domain.notification.NewsNotification;
import com.example.cih.domain.notification.QNewsNotification;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

@Log4j2
public class NewsNotificationSearchImpl extends QuerydslRepositorySupport implements NewsNotificationSearch {
    public NewsNotificationSearchImpl() {
        super(NewsNotification.class);
    }


    @Override
    public Page<NewsNotification> searchAll(String[] types, String keyword, Pageable pageable) {

        QNewsNotification newsNotification = QNewsNotification.newsNotification;
        JPQLQuery<NewsNotification> query = from(newsNotification);

        if (types != null && types.length > 0) {
            BooleanBuilder booleanBuilder = new BooleanBuilder();
            for (String type : types) {
                switch (type) {
                    case "u":
                        booleanBuilder.or(newsNotification.isUse.eq(true));
                        break;
                }
            }
            query.where(booleanBuilder);
        }

        this.getQuerydsl().applyPagination(pageable, query);
        List<NewsNotification> list = query.fetch();
        long count = query.fetchCount();

//        for (NewsNotification event : list) {
//            log.error(event.toString());
//        }

        return new PageImpl<>(list, pageable, count);
    }
}
