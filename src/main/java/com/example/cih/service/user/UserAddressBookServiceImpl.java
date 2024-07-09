package com.example.cih.service.user;

import com.example.cih.domain.user.*;
import com.example.cih.dto.user.UserAddressBookReqDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class UserAddressBookServiceImpl implements UserAddressBookService{

    private final UserAddressBookRepository userAddressBookRepository;


    @Override
    public List<UserAddressBook> getUserAddressBookInfo(User user) {
        return userAddressBookRepository.findByUser(user);
    }

    @Override
    public void registerAddressBook(User user, UserAddressBookReqDTO userAddressBookReqDTO) {

        City city = City.builder()
                .zipcode(userAddressBookReqDTO.getZipcode())
                .country(userAddressBookReqDTO.getCountry())
                .cityName(userAddressBookReqDTO.getCityName())
                .build();

        Address address = Address.builder()
                .city(city)
                .street(userAddressBookReqDTO.getStreet())
                .detailAddress(userAddressBookReqDTO.getDetailAddress())
                .build();

        UserAddressBook userAddressBook = UserAddressBook.builder()
                .user(user)
                .deliveryName(userAddressBookReqDTO.getDeliveryName())
                .RecipientName(userAddressBookReqDTO.getRecipientName())
                .RecipientPhoneNumber(userAddressBookReqDTO.getRecipientPhoneNumber())
                .deliveryRequest(userAddressBookReqDTO.getDeliveryRequest())
                .address(address)
                .build();

        userAddressBookRepository.save(userAddressBook);
    }
}
