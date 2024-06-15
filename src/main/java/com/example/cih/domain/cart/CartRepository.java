package com.example.cih.domain.cart;

import com.example.cih.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
   // Page<Cart> findByUser(User user, Pageable pageable);
    List<Cart> findByUser(User user);
}

