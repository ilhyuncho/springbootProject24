package com.example.cih.domain.notification;


import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED) // 조인 전략
@Table(name="Notification")
public abstract class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="notiId")
    private Long notiId;

    @Column(name="notiMessage", length = 500, nullable = false)
    private String notiMessage;

}
