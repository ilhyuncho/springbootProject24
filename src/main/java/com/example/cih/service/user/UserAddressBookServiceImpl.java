package com.example.cih.service.user;

import com.example.cih.common.exception.OwnerCarNotFoundException;
import com.example.cih.domain.user.*;
import com.example.cih.dto.user.UserAddressBookReqDTO;
import com.example.cih.dto.user.UserAddressBookResDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class UserAddressBookServiceImpl implements UserAddressBookService{

    private final UserAddressBookRepository userAddressBookRepository;

    @Override
    public List<UserAddressBookResDTO> getListUserAddressBook(User user) {

        return userAddressBookRepository.findByUser(user).stream()
                .sorted(Comparator.comparing(UserAddressBook::getIsMainAddress).reversed()) // 기본 배송지 맨 위로
                .map(UserAddressBookServiceImpl::entityToDTO).collect(Collectors.toList());
    }

    @Override
    public UserAddressBookResDTO getUserAddressBookInfo(Long userAddressBookId) {

        UserAddressBook userAddressBook = userAddressBookRepository.findById(userAddressBookId)
                .orElseThrow(() -> new OwnerCarNotFoundException("해당 주소록 정보가 존재하지않습니다"));

        return entityToDTO(userAddressBook);
    }

    @Override
    public void registerAddressBook(User user, UserAddressBookReqDTO userAddressBookReqDTO) {

        List<UserAddressBookResDTO> listUserAddressBook = getListUserAddressBook(user);

        if(isSameDeliveryName(listUserAddressBook, userAddressBookReqDTO))
        {
            throw new OwnerCarNotFoundException("이미 같은 배송지명이 존재 합니다");
        }
        if(listUserAddressBook.size() > 7){
            throw new OwnerCarNotFoundException("배송 주소록을 더 이상 만들수 없습니다");
        }

        // 기본 배송지 체크
        boolean isMainAddress = false;
        if(userAddressBookReqDTO.getMainAddressCheck() || listUserAddressBook.size() == 0){
            isMainAddress = true;
        }

        UserAddressBook userAddressBook = UserAddressBook.builder()
                .user(user)
                .deliveryName(userAddressBookReqDTO.getDeliveryName())
                .RecipientName(userAddressBookReqDTO.getRecipientName())
                .RecipientPhoneNumber(userAddressBookReqDTO.getRecipientPhoneNumber())
                .deliveryRequest(userAddressBookReqDTO.getDeliveryRequest())
                .address(userAddressBookReqDTO.generateAddress())
                .isMainAddress(isMainAddress) // 등록된 배송지 정보가 없으면 기본 배송지로 셋팅
                .build();

        userAddressBookRepository.save(userAddressBook);
    }

    @Override
    public void modifyAddressBook(User user, UserAddressBookReqDTO userAddressBookReqDTO) {

        List<UserAddressBookResDTO> listUserAddressBook = getListUserAddressBook(user);

        if(isSameDeliveryName(listUserAddressBook, userAddressBookReqDTO))
        {
            throw new OwnerCarNotFoundException("이미 같은 배송지명이 존재 합니다");
        }

        UserAddressBook userAddressBook = userAddressBookRepository.findById(userAddressBookReqDTO.getUserAddressBookId())
                .orElseThrow(() -> new NoSuchElementException("해당 배송 주소 정보가 존재하지않습니다"));

        userAddressBook.setAddress(userAddressBookReqDTO.generateAddress());

        userAddressBook.setAddressBookInfo(userAddressBookReqDTO);
    }

    @Override
    public void deleteAddressBook(User user, Long userAddressBookId) {

        UserAddressBook userAddressBook = userAddressBookRepository.findById(userAddressBookId)
                .orElseThrow(() -> new NoSuchElementException("해당 배송 주소 정보가 존재하지않습니다"));

        userAddressBookRepository.delete(userAddressBook);
    }

    public static Boolean isSameDeliveryName(List<UserAddressBookResDTO> listUserAddressBook,
                                             UserAddressBookReqDTO userAddressBookReqDTO){

        return listUserAddressBook.stream()
                .filter(addressBook -> !Objects.equals(addressBook.getUserAddressBookId(), userAddressBookReqDTO.getUserAddressBookId()))
                .anyMatch(addressBook -> addressBook.getDeliveryName().equals(userAddressBookReqDTO.getDeliveryName()));
    }
    private static UserAddressBookResDTO entityToDTO(UserAddressBook userAddressBook) {

        return UserAddressBookResDTO.builder()
                .deliveryName(userAddressBook.getDeliveryName())
                .recipientName(userAddressBook.getRecipientName())
                .recipientPhoneNumber(userAddressBook.getRecipientPhoneNumber())
                .deliveryRequest(userAddressBook.getDeliveryRequest())
                .zipcode(userAddressBook.getAddress().getCity().getZipcode())
                .cityName(userAddressBook.getAddress().getCity().getCityName())
                .country(userAddressBook.getAddress().getCity().getCountry())
                .street(userAddressBook.getAddress().getStreet())
                .detailAddress(userAddressBook.getAddress().getDetailAddress())
                .fullAddress(userAddressBook.getAddress().fullAddress())
                .userAddressBookId(userAddressBook.getUserAddressBookId())
                .mainAddressCheck(userAddressBook.getIsMainAddress())
                .build();
    }

}
