package com.example.cih.domain.car;

import com.example.cih.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {

    Page<Car> findAll(Pageable pageable);

    //Page<Car> findByUser(User user, Pageable pageable);

    Page<Car> findByCarModelContaining(@Param("q") String name, Pageable pageable);

    List<Projection.CarSummary> findByUser(User user);
    List<Projection.CarSummary2> findAllByUser(User user);

}
