package com.example.cih.domain.notification;


import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;

@Entity
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED) // 조인 전략
@Table(name="Notification")
@Immutable                     // 불변 객체 지정, DB에 업데이트 되지 않음, 부모 객체에만 지정하면 됨
public abstract class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="notiId")
    private Long notiId;

    @Column(name="notiMessage", length = 500, nullable = false)
    private String notiMessage;

}
