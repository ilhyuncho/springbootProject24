package com.example.cih.domain.carConsumable;

import com.example.cih.domain.car.Car;
import com.example.cih.domain.carConsumable.search.CarConsumableSearch;
import com.example.cih.domain.reference.RefCarConsumable;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface CarConsumableRepository extends JpaRepository<CarConsumable, Long>, CarConsumableSearch {

    List<CarConsumable> findByCar(Car car);

    List<CarConsumable> findByCarAndRefConsumableId(Car car, RefCarConsumable refCarConsumable);
    List<CarConsumable> findByCarAndConsumableType(Car car, ConsumableType consumableType);
}
