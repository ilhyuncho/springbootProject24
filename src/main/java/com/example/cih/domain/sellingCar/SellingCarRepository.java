package com.example.cih.domain.sellingCar;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface SellingCarRepository  extends JpaRepository<SellingCar, Long> {


}
