package com.example.cih.domain.buyingCar;

import com.example.cih.domain.buyingCar.search.BuyingCarSearch;
import com.example.cih.domain.sellingCar.SellingCar;
import com.example.cih.domain.user.User;
import com.example.cih.dto.buyingCar.BuyingCarViewDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BuyingCarRepository extends JpaRepository<BuyingCar, Long>, BuyingCarSearch {
    List<BuyingCar> findBySellingCar(SellingCar sellingCar);
    Optional<BuyingCar> findBySellingCarAndUserAndIsActive(SellingCar sellingCar, User user, Boolean isActive);

//    @Query(value="select u.userName, b.proposalPrice, b.registerDate from BuyingCar b, User u " +
//            "where b.user.userId = u.userId and b.sellingCar.sellingCarId=?1",
//            countQuery = "select count(b) from BuyingCar b, User u where b.user.userId = u.userId " +
//                    "and b.sellingCar.sellingCarId=?1")
//    Page<Object[]> getBuyingCarInfo(Long sellingCarId, Pageable pageable);

    // JPQL
//    @Query(value="select new com.example.cih.dto.buyingCar.BuyingCarViewDTO(u.userName, b.proposalPrice, b.registerDate) from BuyingCar b, User u " +
//            "where b.user.userId = u.userId and b.sellingCar.sellingCarId=?1")
//    Iterable<BuyingCarViewDTO> getBuyingCarInfo1(Long sellingCarId);

    @Query(value="select new com.example.cih.dto.buyingCar.BuyingCarViewDTO(u.userName, u.memberId, b.proposalPrice, b.buyCarStatus, c.carNumber, c.carModel, c.carId, b.sellingCar.sellingCarId, b.registerTime) " +
                 "from BuyingCar b, User u, Car c where b.user.userId = u.userId " +
            "and b.sellingCar.car.carId = c.carId and b.sellingCar.sellingCarId=?1 and b.isActive = true and b.sellingCar.sellType = 1",

            countQuery = "select count(b) from BuyingCar b, User u where b.user.userId = u.userId " +
                    "and b.sellingCar.sellingCarId=?1 and b.isActive = true and b.sellingCar.sellType = 1")
    Page<BuyingCarViewDTO> getBuyingCarInfo(Long sellingCarId, Pageable pageable);

    List<BuyingCar> findByUser(User user);
}

