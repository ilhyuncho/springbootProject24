package com.example.cih.domain.reference;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="RefPointSituations")
@ToString
public class RefPointSituation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="REF_POINT_SITUATION_ID")
    private Long RefPointSituationId;
    private String situationName;
    private String situationDesc;
    private AccumCycle AccumCycle;      // 적립 사이클 ( 매일, 최초 한번, 임의 )
    private Integer gainPoint;
    private Integer viewOrder;
}
