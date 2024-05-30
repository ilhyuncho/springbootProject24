package com.example.cih.domain.user;

import com.example.cih.common.util.Util;
import com.example.cih.domain.car.Car;
import com.example.cih.domain.car.CarSize;
import com.example.cih.domain.reference.RefCarConsumable;
import com.example.cih.domain.reference.RefCarConsumableRepository;
import com.example.cih.domain.shop.ShopItem;
import com.example.cih.domain.shop.ShopItemRepository;
import lombok.extern.log4j.Log4j2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ShopItemRepository shopItemRepository;

    @Autowired
    RefCarConsumableRepository refCarConsumableRepository;


    int index = 0;


    @Test
    public void InsertUserData(){

        // zip-code 생성  ( Stream 활용 테스트 겸 )
        IntStream randomStream = Util.createRandomStream(2, 100000, 999999);
        List<String> listZipcode = randomStream.mapToObj(String::valueOf).map(a -> {
            StringBuilder buf = new StringBuilder(a);
            buf.insert(3, "-");
            return buf.toString();
        })
        .peek(log::error)
        .collect(Collectors.toList());


        IntStream.rangeClosed(1, 10).forEach(i -> {

            City city = new City(listZipcode.get(0), "buchoen", "korea");
            Address address = Address.builder()
                    .city(city)
                    .street("sudoro257")
                    .detailAddress("2dong404ho")
                    .build();

            City city1 = new City(listZipcode.get(1), "buchoen", "korea");
            Address address1 = Address.builder()
                    .city(city1)
                    .street("sudoro257")
                    .detailAddress("2dong404ho")
                    .build();

            User user = User.builder()
                    .userName("user" + i)
                    .address(address)
                    .billingAddress(address1)
                    .build();

            Long userId = userRepository.save(user).getUserId();
        });


        IntStream.rangeClosed(1,2).forEach(i -> {

            ShopItem shopItem = ShopItem.builder()
                    .itemName("item" + i)
                    .price(1000 * i)
                    .stockCount(10000)
                    .build();

            shopItem.addImage("1a1a1a", "ionic5.png");
            shopItem.addImage("2a2a2a", "ionic51.png");

            shopItem.addItemOption("option1", "option2");

            shopItemRepository.save(shopItem);
        });


        RefCarConsumable refCarConsumable1 = RefCarConsumable.builder()
                .name("주유")
                .repairType("상시")
                .replaceCycleKm(0)
                .replaceCycleMonth(0)
                .viewOrder(0)
                .build();

        refCarConsumableRepository.save(refCarConsumable1);
        /////////////////////////////////
        List<String> listName = new ArrayList<>();
        listName.add("엔진오일 및 오일 필터");
        listName.add("에어컨 필터");
        listName.add("브레이크 오일");
        listName.add("에어클리너 필터");
        listName.add("배터리");
        listName.add("점화플러그");
        listName.add("구동 벨트");
        listName.add("파워스티어링 오일");
        listName.add("브레이크 패드 및 디스크");
        listName.add("미션 오일");


        listName.forEach(name -> {
            int random = (int) (Math.random() * 50);

            RefCarConsumable refCarConsumable = RefCarConsumable.builder()
                    .name(name)
                    .repairType( random % 2 == 0 ? "교체" : "점검" )
                    .replaceCycleKm(5000 + (100 * random))
                    .replaceCycleMonth(12 + random)
                    .viewOrder(index++)
                    .build();

            refCarConsumableRepository.save(refCarConsumable);
        });

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
    @Commit
    public void InsertUserData1(){

        City city = new City("0001111","buchoen", "korea" );
        Address address = Address.builder()
                .city(city)
                .street("sudoro257")
                .detailAddress("2dong404ho")
                .build();

        User user = User.builder()
                .userName("user5")
                .address(address)
                .billingAddress(address)
                .build();


        Long userId = userRepository.save(user).getUserId();

//        Optional<User> user1 = userRepository.findByUserName("user2");
//        Optional<User> user2 = userRepository.findByUserName("user2");
//
//        Assertions.assertEquals(user1, user2);
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
