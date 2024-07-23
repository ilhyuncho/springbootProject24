package com.example.cih.domain.user;

import com.example.cih.domain.sellingCar.SellingCar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserSearchCarHistoryRepository extends JpaRepository<UserSearchCarHistory, Long>{

    Optional<UserSearchCarHistory> findByUserAndSellingCar(User user, SellingCar sellingCar);
    List<UserSearchCarHistory> findByUser(User user);

}

