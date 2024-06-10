package com.example.cih.domain.user;


import com.example.cih.domain.common.BaseEntity;
import com.example.cih.domain.reference.RefMission;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "user")
@Table(name="userMissions")
public class UserMission extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="userMissionId")
    private Long userMissionId;

    @ManyToOne(fetch = FetchType.LAZY)   // 일단 @ManyToOne 단방향
    @JoinColumn(name="uId")
    private User user;

    private RefMissionType refMissionType;

    private Integer gainPoint;

}
