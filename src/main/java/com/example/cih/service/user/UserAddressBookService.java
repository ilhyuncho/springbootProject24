package com.example.cih.service.user;

import com.example.cih.domain.user.User;
import com.example.cih.domain.user.UserAddressBook;
import com.example.cih.dto.user.UserAddressBookReqDTO;
import com.example.cih.dto.user.UserAddressBookResDTO;

import java.util.List;

public interface UserAddressBookService {

    List<UserAddressBook> getListUserAddressBook(User user);
    UserAddressBookResDTO getUserAddressBookInfo(Long userAddressBookId);
    UserAddressBookResDTO getMainAddressInfo(User user);
    List<UserAddressBookResDTO> getAllUserAddressBookInfo(User user);
    void registerAddressBook(User user, UserAddressBookReqDTO userAddressBookReqDTO);
    void modifyAddressBook(User user, UserAddressBookReqDTO userAddressBookReqDTO);
    void deleteAddressBook(User user, Long userAddressBookId);

}
