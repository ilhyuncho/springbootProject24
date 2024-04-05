package com.example.cih.domain.car;

import com.example.cih.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface CarRepository extends JpaRepository<Car, Long> {

    Page<Car> findAll(Pageable pageable);

    Page<Car> findByUser(User user, Pageable pageable);

    Page<Car> findByCarModelContaining(@Param("q") String name, Pageable pageable);

}
