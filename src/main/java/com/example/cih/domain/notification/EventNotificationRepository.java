package com.example.cih.domain.notification;

import com.example.cih.domain.notification.EventNotification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventNotificationRepository extends NotificationRepository<EventNotification, Long> {

}

