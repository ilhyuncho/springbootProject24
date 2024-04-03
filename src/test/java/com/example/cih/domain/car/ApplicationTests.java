package com.example.cih.domain.car;


import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class ApplicationTests {

    @Autowired
    CarRepository carRepository;

    @BeforeAll()
    public void beforeAll(){

        log.error("beforeAll()~~~~~~~~~~~~~~~");
    }

    @BeforeEach()
    public void beforeEach(){
        //carRepository.deleteAll();
        log.error("beforeEach()~~~~~~~~~~~~~~~");
    }

    @AfterEach()
    public void afterEach(){

        log.error("afterEach()~~~~~~~~~~~~~~~");
    }

    @AfterAll
    public void afterAll(){
        log.error("afterAll()~~~~~~~~~~~~~~~");
    }


}
