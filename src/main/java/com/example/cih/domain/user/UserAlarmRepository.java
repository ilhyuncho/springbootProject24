package com.example.cih.domain.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserAlarmRepository extends JpaRepository<UserAlarm, Long> {

    Page<UserAlarm> findByUser(User user, Pageable pageable);


    Long countByUserAndAlarmCheck(User user, boolean alarmCheck);
}

