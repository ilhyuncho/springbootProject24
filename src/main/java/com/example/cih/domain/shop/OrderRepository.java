package com.example.cih.domain.shop;

import com.example.cih.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface OrderRepository extends JpaRepository<Order, Long> {


    List<Order> findByUser(User user);

}

