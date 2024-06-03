package com.example.cih.domain.notification;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NotificationRepository<T extends Notification, ID>
        extends JpaRepository<T, ID> {

    Optional<Notification> findByName(String Name);
}

