package com.example.cih.domain.car;


import com.example.cih.domain.carConsumable.CarConsumable;
import com.example.cih.domain.carConsumable.CarConsumableRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
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

        LocalDate replaceDate = LocalDate.now();

        Optional<Car> byId = carRepository.findById(1L);
        Car car = byId.orElseThrow();

        IntStream.rangeClosed(1,2).forEach(i -> {
            CarConsumable carConsumable = CarConsumable.builder()
                    .refConsumableId(Long.valueOf(i))
                    .replaceDate(replaceDate)
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
