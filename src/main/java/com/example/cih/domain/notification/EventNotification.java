package com.example.cih.domain.notification;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
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
@ToString(callSuper = true)
public class EventNotification extends Notification {

    private LocalDateTime eventStartTime;
    private LocalDateTime eventEndTime;

    private EventType eventType;
    private Integer eventValue;

    public void changeEventInfo(LocalDateTime eventStartTime, LocalDateTime eventEndTime
                               ,EventType eventType, Integer eventValue){
        this.eventStartTime = eventStartTime;
        this.eventEndTime = eventEndTime;
        this.eventType = eventType;
        this.eventValue = eventValue;

    }
}
