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


    private static void initMainAddress(List<UserAddressBook> listUserAddressBook){
        // 기존 [기본 배송지] 설정 false로 변경
        listUserAddressBook.stream()
                .filter(UserAddressBook::getIsMainAddress)
                .forEach(userAddressBook -> {
                    userAddressBook.setMainAddress(false);});
    }

    private static void changeMainAddress(List<UserAddressBook> listUserAddressBook){

        // 최초 등록된 주소로 지정
        listUserAddressBook.forEach(log::error);

        if(listUserAddressBook.size() > 0){
            listUserAddressBook.stream()
                    .min(Comparator.comparing(UserAddressBook::getUserAddressBookId))
                    .get()
                    .setMainAddress(true);
        }

        listUserAddressBook.forEach(log::error);
    }

    private static Boolean isSameDeliveryName(List<UserAddressBook> listUserAddressBook,
                                             UserAddressBookReqDTO userAddressBookReqDTO){

        return listUserAddressBook.stream()
                .filter(addressBook -> !Objects.equals(addressBook.getUserAddressBookId(), userAddressBookReqDTO.getUserAddressBookId()))
                .anyMatch(addressBook -> addressBook.getDeliveryName().equals(userAddressBookReqDTO.getDeliveryName()));
    }

    @Override
    public List<UserAddressBook> getListUserAddressBook(User user) {

        return userAddressBookRepository.findByUser(user).stream()
                .filter(UserAddressBook::getIsActive)
                .collect(Collectors.toList());
    }

    @Override
    public UserAddressBookResDTO getUserAddressBookInfo(Long userAddressBookId) {

        UserAddressBook userAddressBook = userAddressBookRepository.findById(userAddressBookId)
                .orElseThrow(() -> new OwnerCarNotFoundException("해당 주소록 정보가 존재하지않습니다"));

        return entityToDTO(userAddressBook);
    }
    @Override
    public UserAddressBookResDTO getMainAddressInfo(User user) {

        UserAddressBookResDTO userAddressBookResDTO = userAddressBookRepository.findByUser(user).stream()
                .filter(userAddressBook -> userAddressBook.getIsMainAddress() && userAddressBook.getIsActive())
                .map(UserAddressBookServiceImpl::entityToDTO)
                .findFirst().orElse(null);

        return userAddressBookResDTO;
    }

    @Override
    public List<UserAddressBookResDTO> getAllUserAddressBookInfo(User user) {

        List<UserAddressBook> listUserAddressBook = getListUserAddressBook(user);

        return listUserAddressBook.stream()
                .sorted(Comparator.comparing(UserAddressBook::getIsMainAddress).reversed()) // [기본 배송지] 맨 위로
                .map(UserAddressBookServiceImpl::entityToDTO).collect(Collectors.toList());
    }

    @Override
    public void registerAddressBook(User user, UserAddressBookReqDTO userAddressBookReqDTO) {

        List<UserAddressBook> listUserAddressBook = getListUserAddressBook(user);

        // 배송지명 체크
        if(isSameDeliveryName(listUserAddressBook, userAddressBookReqDTO)) {
            throw new OwnerCarNotFoundException("이미 같은 배송지명이 존재 합니다");
        }
        // [배송 주소록] 한도 체크
        if(listUserAddressBook.size() > 7){
            throw new OwnerCarNotFoundException("배송 주소록을 더 이상 만들수 없습니다");
        }

        // [기본 배송지] 체크
        boolean isMainAddress = false;
        if(userAddressBookReqDTO.getMainAddressCheck() || listUserAddressBook.size() == 0){
            isMainAddress = true;

            // 기존 [기본 배송지] 미지정 상태로 변경
           initMainAddress(listUserAddressBook);
        }

        // [신규 배송지] 정보 생성
        UserAddressBook userAddressBook = UserAddressBook.builder()
                .user(user)
                .deliveryName(userAddressBookReqDTO.getDeliveryName())
                .RecipientName(userAddressBookReqDTO.getRecipientName())
                .RecipientPhoneNumber(userAddressBookReqDTO.getRecipientPhoneNumber())
                .deliveryRequest(userAddressBookReqDTO.getDeliveryRequest())
                .address(userAddressBookReqDTO.generateAddress())
                .isMainAddress(isMainAddress) // 등록된 배송지 정보가 없으면 기본 배송지로 셋팅
                .isActive(true)
                .build();

        userAddressBookRepository.save(userAddressBook);
    }

    @Override
    public void modifyAddressBook(User user, UserAddressBookReqDTO userAddressBookReqDTO) {

        UserAddressBook userAddressBook = userAddressBookRepository.findById(userAddressBookReqDTO.getUserAddressBookId())
                .orElseThrow(() -> new NoSuchElementException("해당 배송 주소 정보가 존재하지않습니다"));

        List<UserAddressBook> listUserAddressBook = getListUserAddressBook(user);

        // [배송지명] 체크
        if(isSameDeliveryName(listUserAddressBook, userAddressBookReqDTO)) {
            throw new OwnerCarNotFoundException("이미 같은 배송지명이 존재 합니다");
        }
        // [기존 배송지] 체크
        if(userAddressBookReqDTO.getMainAddressCheck() || listUserAddressBook.size() == 0){
            // 기존 [기본 배송지] 미지정 상태로 변경
            initMainAddress(listUserAddressBook);
        }
        // 수정 대상이 [기본 배송지] 인데 미지정 상태로 변경을 원할 경우
        if(userAddressBook.getIsMainAddress() && !userAddressBookReqDTO.getMainAddressCheck()) {

            List<UserAddressBook> listExceptDeleteAddress = listUserAddressBook.stream()
                    .filter(addressBook -> !Objects.equals(addressBook.getUserAddressBookId(), userAddressBookReqDTO.getUserAddressBookId()))
                    .collect(Collectors.toList());

            changeMainAddress(listExceptDeleteAddress);
        }

        userAddressBook.setAddress(userAddressBookReqDTO.generateAddress());
        userAddressBook.setAddressBookInfo(userAddressBookReqDTO);
    }

    @Override
    public void deleteAddressBook(User user, Long userAddressBookId) {

        UserAddressBook userAddressBook = userAddressBookRepository.findById(userAddressBookId)
                .orElseThrow(() -> new NoSuchElementException("해당 배송 주소 정보가 존재하지않습니다"));

        // 삭제 하려는 주소가 [기본 배송지] 라면
        if(userAddressBook.getIsMainAddress()) {

            List<UserAddressBook> listExceptDeleteAddress = getListUserAddressBook(user).stream()
                    .filter(addressBook -> !Objects.equals(addressBook.getUserAddressBookId(), userAddressBookId))
                    .collect(Collectors.toList());

            changeMainAddress(listExceptDeleteAddress);
        }

        userAddressBook.setActive(false);
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
