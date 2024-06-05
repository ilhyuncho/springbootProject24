package com.example.cih.domain.notification;

import com.example.cih.domain.notification.search.EventNotificationSearch;

public interface EventNotificationRepository extends NotificationRepository<EventNotification, Long>
        , EventNotificationSearch {

}

