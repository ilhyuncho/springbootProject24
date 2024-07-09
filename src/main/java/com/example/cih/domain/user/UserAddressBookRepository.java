package com.example.cih.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserAddressBookRepository extends JpaRepository<UserAddressBook, Long> {
    List<UserAddressBook> findByUser(User user);
}

