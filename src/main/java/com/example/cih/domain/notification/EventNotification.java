package com.example.cih.domain.notification;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="EventNotification")
@PrimaryKeyJoinColumn(name = "EventNotiID")     // 자식 테이블의 기본키 컬럼명을 변경
public class EventNotification extends Notification {

    private LocalDateTime expiredDate;
}
