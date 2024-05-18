package com.example.cih.domain.car;

import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface CarConsumableRepository extends JpaRepository<CarConsumable, Long> {

    List<CarConsumable> findByCar(Car car);
}
