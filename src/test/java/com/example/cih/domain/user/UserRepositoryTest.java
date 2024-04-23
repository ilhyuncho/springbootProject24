package com.example.cih.domain.user;

import com.example.cih.domain.car.Car;
import com.example.cih.domain.car.CarRepository;
import com.example.cih.domain.car.CarSize;
import lombok.extern.log4j.Log4j2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.internal.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    public void InsertUserData(){

        City city = new City("000-111","buchoen", "korea" );
        Address address = Address.builder()
                .city(city)
                .street("sudoro257")
                .detailAddress("2dong404ho")
                .build();

        City city1 = new City("000-111","buchoen", "korea" );
        Address address1 = Address.builder()
                .city(city)
                .street("sudoro257")
                .detailAddress("2dong404ho")
                .build();

        User user = User.builder()
                .userName("user1")
                .address(address)
                .billingAddress(address1)
                .build();

        Long userId = userRepository.save(user).getUserId();

        Optional<User> user1 = userRepository.findByUserName("user1");
        Assertions.assertEquals(user1.get().getUserName(), user.getUserName());
    }

    @Test
    public void InsertUserWithCarsData(){

        User user = User.builder()
                .userName("user3")
                .build();

        List<Car> listCar = IntStream.rangeClosed(1, 2).mapToObj(i ->
                Car.builder()
                        .carModel("model10" + i)
                        .carKm(10000L)
                        .carColors("white")
                        .carYears(2021)
                        .carNumber("25마344" + i)
                        .carGrade(CarSize.LARGE)
                        .build()
        ).collect(Collectors.toList());

        //listCar.forEach(log::error);


        // 유저 객체에 car 등록
        listCar.forEach(user::addMyCars);

        Long userId = userRepository.save(user).getUserId();

        Optional<User> user1 = userRepository.findByUserName("user3");
        Assertions.assertEquals(user1.get().getUserName(), user.getUserName());
    }

//    @BeforeEach
//    @Transactional
//    public void deleteUser(){
//        userRepository.deleteByUserName("user2");
//    }
    @Test
    @Transactional
    public void InsertUserData1(){

        City city = new City("000-111","buchoen", "korea" );
        Address address = Address.builder()
                .city(city)
                .street("sudoro257")
                .detailAddress("2dong404ho")
                .build();

//        User user = User.builder()
//                .userName("user2")
//                .address(address)
//                .build();
//
//
//        Long userId = userRepository.save(user).getUserId();

        Optional<User> user1 = userRepository.findByUserName("user2");
        Optional<User> user2 = userRepository.findByUserName("user2");

        Assertions.assertEquals(user1, user2);
    }

    @Test
    @Transactional
    public void selectWithCars(){

        Optional<User> user1 = userRepository.findByUserName("user1");
        User user = user1.get();

        List<Car> ownCars = user.getOwnCars();

        log.error("---------------이후 로딩---------------------------");
        for (Car ownCar : ownCars) {
            log.error(ownCar);
        }
    }

    @Test
    @Transactional
    @Commit
    public void deleteCars() {
        Optional<User> user1 = userRepository.findByUserName("user3");
        User user = user1.get();

        user.getOwnCars().clear();

        //컬렉션에서 엔티티를 제거하면 db 삭제 가 자동으로 된다
        log.error("------------------------------------------");
    }

    @Test
    @Transactional
    @Commit
    public void deleteUser() {
        userRepository.deleteByUserName("user3");

        // 부모를 삭제 하면 자동으로 자식도 삭제됨
        // orphanRemoval = true
        log.error("------------------------------------------");
    }




}
