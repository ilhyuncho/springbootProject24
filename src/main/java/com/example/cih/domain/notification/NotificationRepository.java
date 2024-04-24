package com.example.cih.domain.notification;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository<T extends Notification, ID>
        extends JpaRepository<T, ID> {


}

