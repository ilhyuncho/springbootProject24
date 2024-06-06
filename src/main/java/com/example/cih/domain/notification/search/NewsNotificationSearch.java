package com.example.cih.domain.notification.search;

import com.example.cih.domain.notification.NewsNotification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NewsNotificationSearch {
    Page<NewsNotification> searchAll(String[] types, String keyword, Pageable pageable);
}
