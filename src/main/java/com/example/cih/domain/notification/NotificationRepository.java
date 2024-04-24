package com.example.cih.domain.notification;

import com.example.cih.domain.test.BillingDetailsRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NotificationRepository<T extends Notification, ID>
        extends JpaRepository<T, ID> {


}

