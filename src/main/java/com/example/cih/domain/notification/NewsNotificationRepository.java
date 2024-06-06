package com.example.cih.domain.notification;

import com.example.cih.domain.notification.search.NewsNotificationSearch;

public interface NewsNotificationRepository extends NotificationRepository<NewsNotification, Long>
        , NewsNotificationSearch {

}

