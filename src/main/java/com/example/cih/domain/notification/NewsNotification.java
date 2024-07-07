package com.example.cih.domain.notification;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="NewsNotification")
@Setter
public class NewsNotification extends Notification{

    @Column(name="target", length = 30, nullable = false)
    private String newsTarget;

    public void changeTarget(String newsTarget){
        this.newsTarget = newsTarget;
    }
}
