package com.example.cih.domain.car;


import com.example.cih.domain.user.Address;
import com.example.cih.domain.user.City;
import com.example.cih.domain.user.User;
import com.example.cih.domain.user.UserRepository;
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

    @Autowired
    UserRepository userRepository;

    User user = User.builder().build();

    @BeforeAll()
    public void beforeAll(){

        user = userRepository.findByUserName("member1").orElseGet(
                () -> {
                    City city = new City("000-222","buchoen", "korea" );
                    Address address = Address.builder()
                            .city(city)
                            .street("sudoro257")
                            .detailAddress("2dong404ho")
                            .build();

                    City city1 = new City("000-222","buchoen", "korea" );
                    Address address1 = Address.builder()
                            .city(city)
                            .street("sudoro257")
                            .detailAddress("2dong404ho")
                            .build();

                    User user = User.builder()
                            .userName("member1")
                            .address(address)
                            .billingAddress(address1)
                            .build();

                    Long userId = userRepository.save(user).getUserId();

                    return user;
                }
        );

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
