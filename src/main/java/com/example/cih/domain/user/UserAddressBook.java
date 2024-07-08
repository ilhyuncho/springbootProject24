package com.example.cih.domain.user;


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

    private String deliveryName;            // 배송지 명
    private String RecipientName;           // 받는 사람 이름
    private String RecipientPhoneNumber;    // 받는 사람 전화번호
    private String deliveryRequest;         // 배송시 요청 사한

    @Embedded   // 임베디드 타입 (복합값)
    private Address address;

}
