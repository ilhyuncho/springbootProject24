package com.example.cih.domain.user;


import com.example.cih.domain.common.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "user")
@Table(name="UserAlarms")
public class UserAlarm extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="userAlarmId")
    private Long userAlarmId;

    private String alarmText;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="UserId")   // pk(외래키)가 user테이블(주테이블)에 생성
    private User user;


}
