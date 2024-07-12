package com.example.cih.domain.user;


import com.example.cih.dto.user.UserAddressBookReqDTO;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name="UserAddressBooks")
public class UserAddressBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="userAddressBookId")
    private Long userAddressBookId;

    @ManyToOne(fetch = FetchType.LAZY)   // 일단 @ManyToOne 단방향
    @JoinColumn(name="uId")
    private User user;
    private Boolean isMainAddress;          // 기본 배송지 유무
    private String deliveryName;            // 배송지 명
    private String RecipientName;           // 받는 사람 이름

    private String RecipientPhoneNumber;    // 받는 사람 전화번호
    private String deliveryRequest;         // 배송시 요청 사한

    @Embedded   // 임베디드 타입 (복합값)
    private Address address;
    private Boolean isActive;               // 활성화 여부


    public void setAddressBookInfo(UserAddressBookReqDTO userAddressBookReqDTO){
        deliveryName = userAddressBookReqDTO.getDeliveryName();
        RecipientName = userAddressBookReqDTO.getRecipientName();
        RecipientPhoneNumber = userAddressBookReqDTO.getRecipientPhoneNumber();
        deliveryRequest = userAddressBookReqDTO.getDeliveryRequest();
        isMainAddress = userAddressBookReqDTO.getMainAddressCheck();
    }
    public void setAddress(Address address){
        this.address = address;
    }

    public void setIsActive(Boolean isActive){
        this.isActive = isActive;
    }
}
