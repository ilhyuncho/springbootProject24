package com.example.cih.domain.user;

import com.example.cih.domain.user.search.UserMissionSearch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserMissionRepository extends JpaRepository<UserMission, Long>, UserMissionSearch {

    List<UserMission>  findByUser(User user);

    @Query("SELECT COUNT(*) FROM UserMission a WHERE a.user = :user")
    int getCountUserMission(@Param("user") User user);


}

