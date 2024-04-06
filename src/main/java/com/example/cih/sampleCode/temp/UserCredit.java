package com.example.cih.sampleCode.temp;


import com.example.cih.domain.user.User;
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

//    @Column(name="uId")
//    private Long userId;

    @Column(name="bankName", length = 10, nullable = false)
    private String bankName;

    @Column(name="bankAccount", length = 20, nullable = false)
    private String bankAccount;

//    @OneToOne(mappedBy = "userCredit")  // 일대일-양방향에서 주인이 아니다
//    private User user;

    @OneToOne
    @JoinColumn(name="UserId")   // pk(외래키)가 user테이블(주테이블)에 생성
    private User user;


}
