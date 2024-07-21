package com.example.cih.domain.car;

import com.example.cih.domain.user.Address;
import com.example.cih.domain.user.User;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.test.annotation.Commit;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@Log4j2
public class CarRepositoryTest extends ApplicationTests {

    @Test
    public void insertCar(){

        // 1, 3 만 생성
        IntStream.iterate(1, idx -> idx < 5,  idx -> idx + 2).forEach(
                i -> {
                    Car car = Car.builder()
                            .carNumber("45마3192" + (i - 1))
                            .carGrade(CarSize.MIDDLE_LARGE)
                            .carModel("model" + (i % 10))
                            .carYears(2010 + (i % 10))
                            .carColors("color" + i)
                            .carKm(10000L + (i % 10))
                            .build();

                    Car result = carRepository.save(car);

                    log.info("BNO: " + result.getCarId());
                }
        );

//        IntStream.rangeClosed(1,100).forEach(i -> {
//            Car car = Car.builder()
//                    .carNumber("45마3192" + (i-1))
//                    .carGrade(CarSize.MIDDLE_LARGE)
//                    .carModel("model" + (i % 10))
//                    .carYears(2010 + (i % 10))
//                    .carColors("color" + i)
//                    .carKm(10000L + (i % 10) )
//                    .build();
//
//            Car result = carRepository.save(car);
//
//            log.info("BNO: " + result.getCarId());
//        });
    }

    @Test
    @Transactional
    @Commit
    public void insertCarWithImages(){

        Car car = Car.builder()
                .carNumber("35마3193")
                .carGrade(CarSize.MIDDLE_LARGE)
                .carModel("model" )
                .carYears(2010)
                .carColors("color")
                .carKm(10000L)
                .user(user)
                .build();

        IntStream.rangeClosed(1, 4).forEach( i -> {
            car.addImage(UUID.randomUUID().toString(),"fileName" + i +".jpg", true );
        });

        Car result = carRepository.save(car);
    }

    @Test
    @Transactional
    @Commit
    public void selectCarWithImages(){

        // 상위 엔티티, 하위 엔티티 어떻게 로딩 하는지
        log.error("======================carRepository.findById before================");
        //Optional<Car> byId = carRepository.findById(1L);

        //@EntityGraph 로 한번에 image파일도 같이 로딩
        Optional<Car> byId = carRepository.findyByWithImages(1L);

        Car car1 = byId.orElseThrow();
        log.error("======================log.error(car1); before================");
        log.error(car1);
        log.error("======================================");
        log.error(car1.getImageSet());
    }

    @Test
    @Transactional
    @Commit
    public void testModifyImages(){

        Optional<Car> byId = carRepository.findyByWithImages(2L);

        Car car = byId.orElseThrow();

        // 기존 파일들 삭제
        car.clearImages();

        // 새롭게 추가
        IntStream.rangeClosed(1, 4).forEach( i -> {
            car.addImage(UUID.randomUUID().toString(),"updatefileName" + i +".jpg", true );
        });

        carRepository.save(car);

    }

    @Test
    public void insertCarWithTemps(){

        Set<String> carTemps = new HashSet<>();
        carTemps.add("image1.jpg");
        carTemps.add("image2.jpg");
        carTemps.add("image3.jpg");
        carTemps.add("image4.jpg");

        Car car = Car.builder()
                .carNumber("45마3193")
                .carGrade(CarSize.MIDDLE_LARGE)
                .carModel("model" )
                .carYears(2010)
                .carColors("color")
                .carKm(10000L)
                .user(user)
                .carTemps(carTemps)
                //.userId(Long.valueOf(i))      // 임시 주석
                .build();

        Car result = carRepository.save(car);

        Car carWithCarTemps = carRepository.findCarWithCarTemps(car.getCarId());

        assertThat(carWithCarTemps.getCarTemps().size()).isEqualTo(4);

        Set<String> carTempsNative = carRepository.findCarTempsNative(car.getCarId());
        assertThat(carTempsNative.size()).isEqualTo(4);

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