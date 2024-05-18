package com.example.cih.domain.car;


import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Log4j2
@SpringBootTest
public class ConsumableRepositoryTests {

    @Autowired
    private CarConsumableRepository carConsumableRepository;

    @Autowired
    private CarRepository carRepository;

    @Test
    public void insertConsumable(){

        LocalDateTime replaceDatetime = LocalDateTime.of(LocalDate.now().minusDays(0),
                LocalTime.of(0, 0, 0));

        Optional<Car> byId = carRepository.findById(1L);
        Car car = byId.orElseThrow();

        IntStream.rangeClosed(1,2).forEach(i -> {
            CarConsumable carConsumable = CarConsumable.builder()
                    .name("소모품" + i)
                    .repairType("타입1")
                    .replaceCycleKm(10000 + (1000 * i))
                    .replaceCycleMonth(12 + i)
                    .replaceDate(replaceDatetime)
                    .car(car)
                    .build();

            carConsumableRepository.save(carConsumable);
        });
    }
    @Test
    public void findConsumable() {

        Optional<Car> byId = carRepository.findById(1L);
        Car car = byId.get();

        List<CarConsumable> byCar = carConsumableRepository.findByCar(car);
        for (CarConsumable carConsumable : byCar) {

            log.error(carConsumable.toString());
        }

    }
}
