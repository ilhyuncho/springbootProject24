package com.example.cih.domain.car;

import com.example.cih.domain.user.Address;
import com.example.cih.domain.user.User;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@Log4j2
public class CarRepositoryTest extends ApplicationTests {


    @Test
    public void insertCar(){

        IntStream.rangeClosed(1,100).forEach(i -> {
            Car car = Car.builder()
                    .carNumber("45마319" + (i-1))
                    .carGrade(CarSize.MIDDLE_LARGE)
                    .carModel("model" + (i % 10))
                    .carYears(2010 + (i % 10))
                    .carColors("color" + i)
                    .carKm(10000L + (i % 10) )
                    //.userId(Long.valueOf(i))      // 임시 주석
                    .build();

            Car result = carRepository.save(car);

            log.info("BNO: " + result.getCarId());
        });
    }

    @Test
    public void insertCarWithImages(){

        User user = userRepository.findByUserName("user1").orElseGet(
                                            () -> User.builder()
                                                    .userName("user1")
                                                    .address(Address.builder().build())
                                                    .billingAddress(Address.builder().build())
                                                    .build()
                                            );

        Set<String> carImages = new HashSet<>();
        carImages.add("image1.jpg");
        carImages.add("image2.jpg");
        carImages.add("image3.jpg");
        carImages.add("image4.jpg");


        Car car = Car.builder()
                .carNumber("45마319")
                .carGrade(CarSize.MIDDLE_LARGE)
                .carModel("model" )
                .carYears(2010)
                .carColors("color")
                .carKm(10000L)
                .user(user)
                .carImages(carImages)
                //.userId(Long.valueOf(i))      // 임시 주석
                .build();

        Car result = carRepository.save(car);

        Car carWithCarImages = carRepository.findCarWithCarImages(car.getCarId());

        assertThat(carWithCarImages.getCarImages().size()).isEqualTo(4);

        Set<String> carImagesNative = carRepository.findCarImagesNative(car.getCarId());
        assertThat(carImagesNative.size()).isEqualTo(4);

    }

    @Test
    public void selectCar(){

        // 고객 정보 get
        Optional<User> user = userRepository.findByUserName("user1");
        User userInfo = user.orElseThrow();


        List<Projection.CarSummary2> allByUser = carRepository.findAllByUser(userInfo);

        log.error(allByUser.get(0).getCarNumber());
        log.error(allByUser.get(0).getCarModel());
        log.error(allByUser.get(1).getCarNumber());
        log.error(allByUser.get(1).getCarModel());
    }



}