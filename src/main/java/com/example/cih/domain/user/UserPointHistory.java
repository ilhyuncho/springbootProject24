package com.example.cih.domain.user;


import com.example.cih.domain.common.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "user")
@Table(name="userPointHistorys")
public class UserPointHistory extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="userPointHistoryId")
    private Long userPointHistoryId;

    @ManyToOne(fetch = FetchType.LAZY)   // 일단 @ManyToOne 단방향
    @JoinColumn(name="uId")
    private User user;

    private PointType pointType;            // 획득 or 소비
    private PointSituation pointSituation;
    private Integer pointValue;
    private String checkValue;

}
