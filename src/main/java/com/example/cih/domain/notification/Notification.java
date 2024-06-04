package com.example.cih.domain.notification;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

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

    private String name;
    private String title;

    @Column(name="message", length = 500, nullable = false)
    private String message;

    private LocalDateTime regDate;

    @OneToMany(mappedBy = "notification", //
            cascade = {CascadeType.ALL}, // ShopItem 엔티티에서 하위 엔티티 객체들을 관리 하는 기능을 추가 해서 사용
            fetch = FetchType.LAZY,
            orphanRemoval = true        // 하위 엔티티가 참조가 더 이상 없는 상태면 삭제 처리 해준다
    )
    @Builder.Default
    @BatchSize(size=20) // N번에 해당하는 쿼리를 모아서 한번에 실행, (N+1문제 해결)
    private Set<NotificationImage> notificationImageSet = new HashSet<>();

    //ShopItem 엔티티 에서 ItemImage 엔티티 객체들을 모두 관리  begin---------------
    public void addImage(String uuid, String fileName){
        NotificationImage notificationImage = NotificationImage.builder()
                .uuid(uuid)
                .fileName(fileName)
                .notification(this)
                .imageOrder(notificationImageSet.size())
                .build();
        notificationImageSet.add(notificationImage);
    }

    public void clearImages(){
        notificationImageSet.forEach(image -> image.changeNotification(null));
        this.notificationImageSet.clear();
    }
    //ShopItem 엔티티 에서 ItemImage 엔티티 객체들을 모두 관리  end---------------

}
