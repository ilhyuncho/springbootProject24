package com.example.cih.domain.notification;

import com.example.cih.domain.notification.search.EventNotificationSearch;

import java.util.List;

public interface EventNotificationRepository extends NotificationRepository<EventNotification, Long>
        , EventNotificationSearch {

    List<EventNotification> findByEventType(EventType eventType);

}

