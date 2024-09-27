package com.example.cih.domain.reference;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.Random;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@Log4j2
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RefCarConsumableRepositoryTest {

    @Autowired
    private RefCarConsumableRepository refCarConsumableRepository;

    @Autowired
    TestRestTemplate testRestTemplate;

    @LocalServerPort
    int port;

    @Test
    public void testRandom(){

        log.error("==================================");
        log.error("port : " + port);
        String result=testRestTemplate.getForObject("/",String.class);
        assertThat(result).isEqualTo("hello namjune");


        Random random = new Random();
        // 4 개 생성, 2~ 9 까지
        random.ints(4, 2, 10).forEach(log::error);
    }
    @Test
    public void insertRefConsumable(){

        IntStream.rangeClosed(1,2).forEach(i -> {
            RefCarConsumable refCarConsumable = RefCarConsumable.builder()
                    .name("소모품1_" + i)
                    .repairType("점검")
                    .replaceCycleKm(10000 + (1000 * i))
                    .replaceCycleMonth(12 + i)
                    .viewOrder(i)
                    .build();

            refCarConsumableRepository.save(refCarConsumable);
        });

        IntStream.rangeClosed(3,4).forEach(i -> {
            RefCarConsumable refCarConsumable = RefCarConsumable.builder()
                    .name("소모품2_" + i)
                    .repairType("교체")
                    .replaceCycleKm(20000 + (1000 * i))
                    .replaceCycleMonth(12 + i)
                    .viewOrder(i)
                    .build();

            refCarConsumableRepository.save(refCarConsumable);
        });
    }
//    @Test
//    public void findConsumable() {
//
//        Optional<Car> byId = carRepository.findById(1L);
//        Car car = byId.get();
//
//        List<CarConsumable> byCar = carConsumableRepository.findByCar(car);
//        for (CarConsumable carConsumable : byCar) {
//
//            log.error(carConsumable.toString());
//        }
//
//    }
}