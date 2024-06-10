package com.example.cih.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserMissionRepository extends JpaRepository<UserMission, Long> {

    List<UserMission>  findByUser(User user);
}

