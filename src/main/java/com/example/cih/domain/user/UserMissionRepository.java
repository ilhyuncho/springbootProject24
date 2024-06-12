package com.example.cih.domain.user;

import com.example.cih.domain.user.search.UserMissionSearch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserMissionRepository extends JpaRepository<UserMission, Long>, UserMissionSearch {

    List<UserMission>  findByUser(User user);
}

