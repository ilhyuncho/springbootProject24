package com.example.cih.sampleCode.temp;


import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name="UserCredits")
public class UserCredit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="userCreditsId")
    private Long userCreditsId;

    @Column(name="uId")
    private Long userId;

    @Column(name="bankName", length = 10, nullable = false)
    private String bankName;

    @Column(name="bankAccount", length = 20, nullable = false)
    private String bankAccount;



}
