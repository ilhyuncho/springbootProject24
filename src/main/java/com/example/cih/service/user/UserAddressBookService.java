package com.example.cih.service.user;

import com.example.cih.domain.user.User;
import com.example.cih.domain.user.UserAddressBook;
import com.example.cih.dto.user.UserAddressBookReqDTO;

import java.util.List;

public interface UserAddressBookService {

    List<UserAddressBook> getUserAddressBookInfo(User user);
    void registerAddressBook(User user, UserAddressBookReqDTO userAddressBookReqDTO);

}
