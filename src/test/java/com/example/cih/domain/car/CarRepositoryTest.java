package com.example.cih.domain.car;

import com.example.cih.domain.board.Board;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
class CarRepositoryTest {

    @Autowired
    private CarRepository carRepository;

    @BeforeEach()
    public void beforeEach(){
        //carRepository.deleteAll();
        log.error("beforeEach()~~~~~~~~~~~~~~~");
    }
    @Test
    public void insertCar(){

        IntStream.rangeClosed(1,100).forEach(i -> {
            Car car = Car.builder()
                    .carNumber("35마319" + (i-1))
                    .carGrade(CarSize.MIDDLE_LARGE)
                    .carModel("model" + (i % 10))
                    .carYears(2010 + (i % 10))
                    .carColors("color" + i)
                    .carKm(10000L + (i % 10) )
                    .userId(Long.valueOf(i))
                    .build();

            Car result = carRepository.save(car);

            log.info("BNO: " + result.getCarId());
        });
    }


}