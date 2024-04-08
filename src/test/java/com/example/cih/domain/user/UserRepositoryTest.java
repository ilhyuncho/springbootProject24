package com.example.cih.domain.user;

import lombok.extern.log4j.Log4j2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.Optional;

@SpringBootTest
@Log4j2
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    public void InsertUserData(){

        Address address = Address.builder()
                .city("buchoen")
                .street("sudoro257")
                .detailAddress("2dong404ho")
                .zipcode("234-2323")
                .build();

        User user = User.builder()
                .userName("user1")
                .address(address)
                .build();

        Long userId = userRepository.save(user).getUserId();

        Optional<User> user1 = userRepository.findByUserName("user1");
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

        Address address = Address.builder()
                .city("seoul")
                .street("sudoro257")
                .detailAddress("2dong404ho")
                .zipcode("234-2323")
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




}
