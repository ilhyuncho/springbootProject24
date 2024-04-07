package com.example.cih.domain.notification;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="NewsNotification")
public class NewsNotification extends Notification{

    @Column(name="notiTarget", length = 30, nullable = false)
    private String notiTarget;
}
