package com.example.cih.domain.notification;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="EventNotification")
@PrimaryKeyJoinColumn(name = "EventNotiID")     // 자식 테이블의 기본키 컬럼명을 변경
@ToString(callSuper = true)
public class EventNotification extends Notification {

    private LocalDate eventStartDate;
    private LocalDate eventEndDate;

    private EventType eventType;
    private Integer eventValue;

    public void changeEventInfo(LocalDate eventStartDate, LocalDate eventEndDate
                               ,EventType eventType, Integer eventValue){
        this.eventStartDate = eventStartDate;
        this.eventEndDate = eventEndDate;
        this.eventType = eventType;
        this.eventValue = eventValue;

    }
}
