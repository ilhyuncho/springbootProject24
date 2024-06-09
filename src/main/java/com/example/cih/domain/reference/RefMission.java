package com.example.cih.domain.reference;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="refMissions")
@ToString
public class RefMission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="REF_MISSION_ID")
    private Long refMissionId;
    private String missionName;
    private String missionDesc;
    private AccumCycle AccumCycle;      // 적립 사이클 ( 매일, 최초 한번, 임의 )
    private Integer gainPoint;
    private Integer viewOrder;
}
