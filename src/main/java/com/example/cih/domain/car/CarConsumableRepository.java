package com.example.cih.domain.car;

import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;

public interface CarConsumableRepository extends JpaRepository<CarConsumable, Long> {

    List<CarConsumable> findByCar(Car car);

    List<CarConsumable> findByCarAndRefConsumableId(Car car, Long refConsumableId);
}
