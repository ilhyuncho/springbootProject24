package com.example.cih.domain.reference;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="refCarSamples")
@ToString
public class RefCarSample {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="refCarSampleId")
    private Long refCarSampleId;

    private String carNumber;
    private String carModel;
    private String carGrade;        // 등급 ( 소형, 중형, 대형.. )
    private String carOption;       // 옵션
    private String company;
    private String companyNation;
    private Integer modelYear;      // 출시 연도
    private Integer carKm;          // 주행거리
    private LocalDate regDate;      // 최초 등록 날짜
}
