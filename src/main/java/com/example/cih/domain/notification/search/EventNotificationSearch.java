package com.example.cih.domain.notification.search;

import com.example.cih.domain.notification.EventNotification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EventNotificationSearch {
    Page<EventNotification> searchAll(String[] types, String keyword, Pageable pageable);
}
