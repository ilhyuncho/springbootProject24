package com.example.cih.sampleCode.temp;

import com.example.cih.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserCreditRepository extends JpaRepository<UserCredit, Long> {


    Optional<UserCredit> findByUser(User user);
}

