package com.example.cih.domain.reference;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="refCarConsumables")
@ToString
public class RefCarConsumable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="REF_CONSUMABLE_ID")
    private Long refConsumableId;
    private String name;
    private String repairType;      // 점검 or 교체
    private int replaceCycleKm;
    private int replaceCycleMonth;
    private int viewOrder;
}
